package com.yanbal.lib.corp.notificaciones;

/**
 * Clase para el envio de notificaciones via e-mail /**
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


public class NotificacionMail implements com.yanbal.lib.corp.interfaces.INotificacion {
  /** Servidor SMTP para el envio de correos */
  private Object servidor;

  /** Usuario para el envio de correo */
  private Object usuario;

  /** Contrase�a para el envio de correo */
  private Object contrasenha;


  /**
   * Metodo que envia un e-mail de notificacion
   * 
   * @param listaDestinatarios Destinatarios del correo
   * @param asunto asunto del correo
   * @param mensaje mensaje del correo
   */
  public void enviar(Object listaDestinatarios, Object asunto, Object mensaje)
      throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
  }


}
