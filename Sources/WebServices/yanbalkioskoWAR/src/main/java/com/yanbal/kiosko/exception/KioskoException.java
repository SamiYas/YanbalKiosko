package com.yanbal.kiosko.exception;

/**
 * Exception personalizado para manejar mensajes de error
 * 
 * @author lennin.davila
 * 
 */
public class KioskoException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor por defecto
   */
  public KioskoException() {
    super();
  }

  /**
   * Constructor creado a partir de un mensaje
   * 
   * @param mensaje detalle del mensaje
   */
  public KioskoException(String mensaje) {
    super(mensaje);
  }

  /**
   * Constructor creado a partir de un mensaje y una excepcion enviada
   * 
   * @param mensaje detalle del mensaje
   * @param thr excepcion enviada
   */
  public KioskoException(String mensaje, Throwable thr) {
    super(mensaje, thr);
  }
}
