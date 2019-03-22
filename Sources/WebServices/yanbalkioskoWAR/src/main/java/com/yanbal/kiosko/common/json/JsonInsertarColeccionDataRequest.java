package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * insertar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonInsertarColeccionDataRequest {

  private String token;

  @SerializedName("id_coleccion_padre")
  private Long idPadre;
  private Long pais;
  private String nombre;
  private Integer nivel;
  private Integer orden;
  private String descripcion;
  private String color;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getIdPadre() {
    return idPadre;
  }

  public void setIdPadre(Long idPadre) {
    this.idPadre = idPadre;
  }

  public Long getPais() {
    return pais;
  }

  public void setPais(Long pais) {
    this.pais = pais;
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

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
