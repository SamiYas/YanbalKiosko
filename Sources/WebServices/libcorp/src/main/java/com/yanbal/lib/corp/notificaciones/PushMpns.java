package com.yanbal.lib.corp.notificaciones;

import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.android.gcm.server.MulticastResult;
import com.yanbal.lib.corp.entities.MultiResultadoPush;
import com.yanbal.lib.corp.interfaces.IPushService;

/**
 * Clase de implementacion que envia las notificaciones push a Windows
 * 
 * @author lennin.davila
 *
 */
public class PushMpns implements IPushService {
  private static final Logger LOGGER = LogManager.getLogger(PushMpns.class);
  static final String WINDOWS_PLATFORM = "WINDOWS_PLATFORM";
  static final String PUSH_WINDOWS_PROPERTIES_FILE = "push/pushWindowsPhone.properties";
  MulticastResult result = null;

  /**
   * Constructor de clase
   */
  public PushMpns() {}

  /**
   * Metodo que retorna el mensaje formateado para Windows
   */
  private String getMessageFormat(String titulo, String subtitulo) {
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        + "<wp:Notification xmlns:wp=\"WPNotification\">" + "<wp:Toast>" + "<wp:Text1>" + titulo
        + "</wp:Text1>" + "<wp:Text2>" + subtitulo + "</wp:Text2>"
        + "<wp:Param>/Page2.xaml?NavigatedFrom=Toast Notification</wp:Param>" + "</wp:Toast> "
        + "</wp:Notification>";

  }

  /**
   * Metodo que envia las notificaciones push al servidor Windows
   */
  @Override
  public MultiResultadoPush push(List<String> tokenDevice, String message, int reintento) {
    String msgFormateado = getMessageFormat("", message);
    MultiResultadoPush resultResport = new MultiResultadoPush();
    List<String> tokensFail = new ArrayList<String>();
    StringBuilder mensajeError = new StringBuilder(PushMpns.WINDOWS_PLATFORM).append(": ");
    int success = 0;

    for (String token : tokenDevice) {
      try {
        URLConnection connection = new URL(token).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("ContentType", "text/xml");
        connection.setRequestProperty("ContentLength", msgFormateado.getBytes().length + "");
        connection.setRequestProperty("X-WindowsPhone-Target", "toast");
        connection.setRequestProperty("X-NotificationClass", "2");

        OutputStream output = connection.getOutputStream();
        output.write(msgFormateado.getBytes(), 0, msgFormateado.getBytes().length);
        success++;
        output.close();

        String xNotificationStatus =
            connection.getHeaderFields().get("X-NotificationStatus").toString();
        String xSubscriptionStatus =
            connection.getHeaderFields().get("X-SubscriptionStatus").toString();
        String xDeviceConnectionStatu =
            connection.getHeaderFields().get("X-DeviceConnectionStatus").toString();
        StringBuilder sbm = new StringBuilder();
        sbm.append("xNotificationStatus: ").append(xNotificationStatus).append(" ");
        sbm.append("xSubscriptionStatus: ").append(xSubscriptionStatus).append(" ");
        sbm.append("xDeviceConnectionStatu: ").append(xDeviceConnectionStatu);
        LOGGER.info(sbm);
      } catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        mensajeError.append(ex.getMessage()).append(" ");
        tokensFail.add(token);
      }
    }
    resultResport.setSuccess(success);
    resultResport.setFailure(tokensFail.size());
    resultResport.setTokenFails(tokensFail);
    resultResport.setMensaje(mensajeError.toString());
    resultResport.setPlataforma(PushMpns.WINDOWS_PLATFORM);
    return resultResport;
  }
}
