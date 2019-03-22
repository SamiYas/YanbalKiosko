package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerColeccionDataResponse implements JsonKioskoDataResponse {

  @SerializedName("id_coleccion")
  private Long id;
  @SerializedName("id_coleccion_padre")
  private Long idPadre;
  @SerializedName("nombre_Coleccion_Padre")
  private String nombreColeccionPadre;
  private String nombre;
  private Integer nivel;
  private Integer orden;
  private String descripcion;
  private String color;



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

  public String getNombreColeccionPadre() {
    return nombreColeccionPadre;
  }

  public void setNombreColeccionPadre(String nombreColeccionPadre) {
    this.nombreColeccionPadre = nombreColeccionPadre;
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
