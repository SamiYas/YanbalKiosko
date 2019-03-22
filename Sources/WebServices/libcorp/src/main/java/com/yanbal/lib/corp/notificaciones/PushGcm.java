package com.yanbal.lib.corp.notificaciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.yanbal.lib.corp.aplicacion.Propiedades;
import com.yanbal.lib.corp.entities.MultiResultadoPush;
import com.yanbal.lib.corp.interfaces.IPushService;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Clase de implementacion que envia las notificaciones push a Google
 * 
 * @author lennin.davila
 *
 */
public class PushGcm implements IPushService {
  private static final Logger LOGGER = LogManager.getLogger(PushGcm.class);
  static final String ANDROID_PLATFORM = "ANDROID_PLATFORM";
  static final String PUSH_ANDROID_PROPERTIES_FILE = "push/pushAndroid.properties";
  static final int LIMIT_BATCH_TASK = 1000;
  static final String MESSAGE_KEY = "message";
  MulticastResult result = null;
  private Sender service = null;

  /**
   * Constructor de clase
   */
  public PushGcm() throws LibCorpException {
    Propiedades propiedades;
    try {
      propiedades = new Propiedades(PUSH_ANDROID_PROPERTIES_FILE);
      String googleServerKey = propiedades.getValor("push.google_server_key");
      service = new Sender(googleServerKey);
    } catch (Exception ex) {
      throw new LibCorpException(ex.getMessage(), ex);
    }
  }

  /**
   * Metodo que envia las notificaciones push al servidor Google
   */
  private int sendGcm(List<String> tokenDevice, String message, int reintento) throws IOException {
    Message gcmmsg =
        new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY, message)
            .build();
    MulticastResult resultadogcm = service.send(gcmmsg, tokenDevice, reintento);
    return resultadogcm.getSuccess();
  }

  /**
   * Metodo que gestiona el envio de notificaciones push a Google
   */
  @Override
  public MultiResultadoPush push(List<String> tokenDevice, String message, int reintento) {
    MultiResultadoPush resultResport = new MultiResultadoPush();
    List<String> tokensFail = new ArrayList<String>();
    StringBuilder mensajeError = new StringBuilder(PushGcm.ANDROID_PLATFORM).append(": ");
    int size = tokenDevice.size();
    int success = 0;

    List<List<String>> resultTemp = new ArrayList<List<String>>();
    int comp = size / LIMIT_BATCH_TASK;
    int fromIndex = 0;
    int toIndex = LIMIT_BATCH_TASK;
    if (size <= LIMIT_BATCH_TASK) {
      resultTemp.add(tokenDevice);
    } else {
      for (int count = 0; count <= comp; count++) {
        if (count == comp) {
          resultTemp.add(tokenDevice.subList(fromIndex, size));
        } else {
          resultTemp.add(tokenDevice.subList(fromIndex, toIndex));
          fromIndex += (LIMIT_BATCH_TASK);
          toIndex += (LIMIT_BATCH_TASK);
        }
      }
    }

    for (List<String> tokens : resultTemp) {
      try {
        success += sendGcm(tokens, message, reintento);
      } catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        mensajeError.append(ex.getMessage()).append(" ");
        tokensFail.addAll(tokens);
      }
    }

    resultResport.setSuccess(success);
    resultResport.setFailure(tokensFail.size());
    resultResport.setTokenFails(tokensFail);
    resultResport.setMensaje(mensajeError.toString());
    resultResport.setPlataforma(PushGcm.ANDROID_PLATFORM);
    return resultResport;
  }
}
