package com.yanbal.lib.corp.notificaciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.yanbal.lib.corp.aplicacion.Propiedades;
import com.yanbal.lib.corp.bo.PushEnvioBO;
import com.yanbal.lib.corp.entities.MultiResultadoPush;
import com.yanbal.lib.corp.entities.PushEnvio;
import com.yanbal.lib.corp.entities.TokenPush;
import com.yanbal.lib.corp.enums.Plataforma;
import com.yanbal.lib.corp.interfaces.FactoryPushMessager;
import com.yanbal.lib.corp.interfaces.IPushService;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Clase principal de envio de notificiaciones Push
 * 
 * @author lennin.davila
 *
 */
public class ClientePush {
  private static final Logger LOGGER = LogManager.getLogger(ClientePush.class);
  static final String PUSH_PROPERTIES_FILE = "push/notification.properties";
  int limiteErrores = 0;
  int tiempoPausaError = 1000;
  int limiteReintentosNoDisponibilidad = 0;


  /**
   * Constructor de clase
   */
  public ClientePush() throws LibCorpException {
    Propiedades propiedades;
    try {
      propiedades = new Propiedades(PUSH_PROPERTIES_FILE);
      limiteErrores = Integer.parseInt(propiedades.getValor("push.LimiteErrores"));
      tiempoPausaError = Integer.parseInt(propiedades.getValor("push.TiempoPausaError"));
      limiteReintentosNoDisponibilidad =
          Integer.parseInt(propiedades.getValor("push.LimiteReintentosNoDisponibilidad"));
    } catch (Exception ex) {
      throw new LibCorpException(ex.getMessage(), ex);
    }
  }

  /**
   * Metodo para enviar notificaciones
   * 
   * @param lista : lista con los tokens para enviar notificaciones push
   * @param mensaje : mensaje a enviar en las notificaciones push
   * @return plataforma : El enumerador que especifica la plataforma a enviar las notificaciones
   *         push
   * @return datasourcename : nombre del datasource
   * @return pais : nombre del pais
   */
  public void notificarPlataforma(List<String> lista, String mensaje, Plataforma plataforma,
      String datasourcename, String pais) throws LibCorpException {
    MultiResultadoPush resultado = null;
    PushEnvio pushEnvio = new PushEnvio();
    pushEnvio.setFechaHoraInicio(new Date());
    long success = 0;
    long failure = 0;

    if (!lista.isEmpty()) {
      resultado = push(lista, mensaje, plataforma);
      success += resultado.getSuccess();
      failure += resultado.getFailure();
    }

    if (resultado != null) {
      // Gemneramos mensaje por plataforma a enviar
      pushEnvio.setErrores(failure);
      pushEnvio.setExitosos(success);
      pushEnvio.setFechaHoraFin(new Date());
      pushEnvio.setMensaje(resultado.getMensaje());
      pushEnvio.setPais(pais);
      pushEnvio.setPlataforma(plataforma.toString());

      // Enviamos mensaje
      try {
        PushEnvioBO pushEnvioBO = new PushEnvioBO(datasourcename);
        pushEnvioBO.insertarPushEnvio(pushEnvio);
      } catch (Exception ex) {
        throw new LibCorpException(ex.getMessage(), ex);
      }
    }
  }

  /**
   * Metodo principal para enviar notificaciones push a las diferentes principales
   * 
   * @param lista : lista con los tokens para enviar notificaciones push
   * @param pais : nombre del pais a enviar
   * @param mensaje : mensaje a enviar en las notificaciones push
   * @return datasourcename : nombre del datasource
   */
  public void notificar(List<TokenPush> lista, String pais, String mensaje, String datasourcename)
      throws LibCorpException {
    try {
      List<String> listaApple = new ArrayList<String>();
      List<String> listaAndroid = new ArrayList<String>();
      List<String> listaWindows = new ArrayList<String>();

      for (TokenPush token : lista) {
        if (Plataforma.ANDROID_PLATFORM == token.getPlataforma()) {
          listaAndroid.add(token.getTokenDevice());
        }
        if (Plataforma.IOS_PLATFORM == token.getPlataforma()) {
          listaApple.add(token.getTokenDevice());
        }
        if (Plataforma.WINDOWS_PLATFORM == token.getPlataforma())
          listaWindows.add(token.getTokenDevice());
      }

      notificarPlataforma(listaAndroid, mensaje, Plataforma.ANDROID_PLATFORM, datasourcename, pais);
      notificarPlataforma(listaWindows, mensaje, Plataforma.WINDOWS_PLATFORM, datasourcename, pais);
      notificarPlataforma(listaApple, mensaje, Plataforma.IOS_PLATFORM, datasourcename, pais);
    } catch (Exception ex) {
      throw new LibCorpException(ex.getMessage(), ex);
    }
  }

  /**
   * Metodo para enviar notificaciones
   * 
   * @param tokens : lista con los tokens para enviar notificaciones push
   * @param mensaje : mensaje a enviar en las notificaciones push
   * @return plataforma : El enumerador que especifica la plataforma a enviar las notificaciones
   *         push
   */
  private MultiResultadoPush push(List<String> tokens, String mensaje, Plataforma plataforma)
      throws LibCorpException {
    FactoryPushMessager factoryPushMessager = FactoryPushMessager.getPushFactory(plataforma);
    IPushService iPushService = factoryPushMessager.getPushService();
    MultiResultadoPush resultado = null;
    try {
      resultado = iPushService.push(tokens, mensaje, limiteReintentosNoDisponibilidad);
      if (resultado.getFailure() > 0)
        throw new LibCorpException("Error durante el envio de notificaciones push");
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      sleep(tiempoPausaError);
      resultado = reintentar(resultado.getTokenFails(), mensaje, iPushService);
    }
    return resultado;
  }

  /**
   * Metodo para realizar los reintentos
   * 
   * @param tokenDevice : lista con los tokens para enviar los reintentos
   * @param mensaje : mensaje a enviar en las notificaciones push
   */
  private MultiResultadoPush reintentar(List<String> tokenDevice, String message,
      IPushService pushService) throws LibCorpException {
    MultiResultadoPush resultado = null;
    int errorCount = 1;
    int retry = 0;
    do {
      try {
        if (errorCount == limiteErrores)
          return resultado;
        retry++;
        resultado = pushService.push(tokenDevice, message, limiteErrores);
        if (resultado != null)
          throw new LibCorpException("Error durante el envio de notificaciones push");
      } catch (Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        if (exc != null) {
          errorCount++;
          sleep(tiempoPausaError);
        }
      }
    } while (retry <= limiteReintentosNoDisponibilidad);
    return resultado;
  }

  /**
   * Metodo para realizar una pausa al proceso
   * 
   * @param millis : tiempo en milisegundos que esperara antes de continuar el proceso
   */
  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
  }
}
