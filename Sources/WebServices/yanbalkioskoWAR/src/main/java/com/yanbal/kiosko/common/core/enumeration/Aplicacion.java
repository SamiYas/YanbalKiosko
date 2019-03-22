package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enumerador para las aplicaciones
 * 
 * @author alex.contreras
 * 
 */
public enum Aplicacion {

  MOVIL("KIOSKOV2MOVIL"), WEB("KIOSKOV2WEB");

  private String nombre;

  private Aplicacion(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

}
