package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * actualizar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonActualizarColeccionDataRequest {

  private String token;
  @SerializedName("id_coleccion")
  private Long id;
  @SerializedName("id_coleccion_padre")
  private Long idPadre;
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

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }


}
