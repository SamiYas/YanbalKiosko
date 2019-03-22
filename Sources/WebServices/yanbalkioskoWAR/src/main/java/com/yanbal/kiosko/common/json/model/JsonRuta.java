package com.yanbal.kiosko.common.json.model;

/**
 * Clase plantilla para un objeto Ruta
 * 
 * @author alex.contreras
 * 
 */
public class JsonRuta {

  private String nombre;
  private Long id;
  private Integer nivel;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNivel() {
    return nivel;
  }

  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }

}
