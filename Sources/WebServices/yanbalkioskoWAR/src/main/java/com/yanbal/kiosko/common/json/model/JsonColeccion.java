package com.yanbal.kiosko.common.json.model;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para un objeto Coleccion
 * 
 * @author alex.contreras
 *
 */
public class JsonColeccion {

  @SerializedName("id_coleccion")
  private Long id;
  @SerializedName("id_coleccion_padre")
  private Long idPadre;

  private String nombre;
  private Integer nivel;
  private Integer orden;
  private String descripcion;
  private Integer coleccionesHijas;
  private Long tipoUsuario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdPadre() {
    return idPadre;
  }

  public void setIdPadre(Long idPadre) {
    this.idPadre = idPadre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Integer getNivel() {
    return nivel;
  }

  public void setNivel(Integer nivel) {
    this.nivel = nivel;
  }

  public Integer getOrden() {
    return orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getColeccionesHijas() {
    return coleccionesHijas;
  }

  public void setColeccionesHijas(Integer coleccionesHijas) {
    this.coleccionesHijas = coleccionesHijas;
  }

  public Long getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(Long tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

}
