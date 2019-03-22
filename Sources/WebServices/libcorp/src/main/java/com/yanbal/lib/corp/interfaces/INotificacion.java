package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la gestion de notificaciones
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */
public interface INotificacion {

  /**
   * Metodo que envia un e-mail de notificacion
   * 
   * @param listaDestinatarios Destinatarios del correo
   * @param asunto asunto del correo
   * @param mensaje mensaje del correo
   */
  public void enviar(Object listaDestinatarios, Object asunto, Object mensaje);
}
