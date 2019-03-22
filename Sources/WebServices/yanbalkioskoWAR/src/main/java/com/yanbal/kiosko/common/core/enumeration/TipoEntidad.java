package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enumerador para los Tipos de Entidad
 * 
 * @author alex.contreras
 * 
 */
public enum TipoEntidad {

  ARCHIVO(1), COLECCION(2), FORMATO_ARCHIVO(3);

  private int valor;

  private TipoEntidad(int valor) {
    this.valor = valor;
  }

  public int getValor() {
    return valor;
  }

}
