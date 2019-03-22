package com.yanbal.lib.corp.notificaciones;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.yanbal.lib.corp.aplicacion.Propiedades;
import com.yanbal.lib.corp.entities.MultiResultadoPush;
import com.yanbal.lib.corp.interfaces.IPushService;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Clase de implementacion que envia las notificaciones push a IOS
 * 
 * @author lennin.davila
 *
 */
public class PushApns implements IPushService {
  private static final Logger LOGGER = LogManager.getLogger(PushApns.class);
  static final String IOS_PLATFORM = "IOS_PLATFORM";
  static final String PUSH_IOS_PROPERTIES_FILE = "push/pushIOS.properties";
  private ApnsService service = null;

  /**
   * Constructor de clase
   */
  public PushApns() throws LibCorpException {
    Propiedades propiedades;
    try {
      propiedades = new Propiedades(PUSH_IOS_PROPERTIES_FILE);
      String keyp12 = propiedades.getValor("push.keyp12");
      String password = propiedades.getValor("push.password");
      String production = propiedades.getValor("push.entorno");

      ApnsServiceBuilder sbuilder = APNS.newService();
      sbuilder.withCert(keyp12, password);
      if ("PRODUCTION".equalsIgnoreCase(production)) {
        sbuilder.withProductionDestination();
      } else {
        sbuilder.withSandboxDestination();
      }
      service = (ApnsService) sbuilder.build();
      service.start();
    } catch (Exception ex) {
      throw new LibCorpException(ex.getMessage(), ex);
    }
  }

  /**
   * Metodo que envia las notificaciones al servidor IOS
   */
  @SuppressWarnings("unchecked")
  @Override
  public MultiResultadoPush push(List<String> tokenDevice, String message, int reintento) {
    MultiResultadoPush resultResport = new MultiResultadoPush();
    List<String> tokensFail = new ArrayList<String>();
    StringBuilder mensajeError = new StringBuilder(PushApns.IOS_PLATFORM).append(": ");

    String payLoad = APNS.newPayload().alertBody(message).build();
    try {
      service.push(tokenDevice, payLoad);
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      mensajeError.append(ex.getMessage()).append(" ");
      tokensFail.addAll(tokenDevice);
    }
    if (!tokensFail.isEmpty()) {
      resultResport.setSuccess(0);
      resultResport.setFailure(tokensFail.size());
      resultResport.setTokenFails(tokensFail);
    } else {
      resultResport.setSuccess(tokenDevice.size());
      resultResport.setFailure(0);
      resultResport.setTokenFails(tokensFail);
    }
    resultResport.setMensaje(mensajeError.toString());
    resultResport.setPlataforma(PushApns.IOS_PLATFORM);
    return resultResport;
  }
}
