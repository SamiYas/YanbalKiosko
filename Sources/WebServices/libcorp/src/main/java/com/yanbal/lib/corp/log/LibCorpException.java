package com.yanbal.lib.corp.log;

public class LibCorpException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor por defecto
   */
  public LibCorpException() {
    super();
  }

  /**
   * Constructor creado a partir de un mensaje
   * 
   * @param mensaje detalle del mensaje
   */
  public LibCorpException(String mensaje) {
    super(mensaje);
  }

  /**
   * Constructor creado a partir de un mensaje y una excepcion enviada
   * 
   * @param mensaje detalle del mensaje
   * @param thr excepcion enviada
   */
  public LibCorpException(String mensaje, Throwable thr) {
    super(mensaje, thr);
  }
}

