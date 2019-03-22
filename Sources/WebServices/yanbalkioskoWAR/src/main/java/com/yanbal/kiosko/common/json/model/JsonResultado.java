package com.yanbal.kiosko.common.json.model;

/**
 * Clase plantilla para un objeto Resultado
 * 
 * @author alex.contreras
 * 
 */
public class JsonResultado {

  private Long rol;

  public JsonResultado() {    
  }

  public JsonResultado(Long rol) {
    this.rol = rol;
  }

  public Long getRol() {
    return rol;
  }

  public void setRol(Long rol) {
    this.rol = rol;
  }

}
