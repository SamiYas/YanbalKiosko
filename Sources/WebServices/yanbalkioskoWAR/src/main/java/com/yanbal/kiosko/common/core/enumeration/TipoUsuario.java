package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enumerador para los Tipos de Usuario
 * 
 * @author juan.gutierrez
 * 
 */
public enum TipoUsuario {

  CORP(6), UN(7);

  private int correlativoRol;

  private TipoUsuario(int correlativoRol) {
    this.correlativoRol = correlativoRol;
  }

  public int getCorrelativoRol() {
    return correlativoRol;
  }

}
