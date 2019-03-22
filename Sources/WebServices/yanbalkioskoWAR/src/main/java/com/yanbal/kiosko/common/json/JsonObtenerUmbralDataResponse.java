package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener umbral
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerUmbralDataResponse implements JsonKioskoDataResponse {

  private Long id;
  private Long rol;
  private String extension;
  private String descripcion;
  private Double carga;
  private Double descarga;

  public Long getId() {
    return id;
  }

  public Long getRol() {
    return rol;
  }

  public String getExtension() {
    return extension;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Double getCarga() {
    return carga;
  }

  public Double getDescarga() {
    return descarga;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRol(Long rol) {
    this.rol = rol;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCarga(Double carga) {
    this.carga = carga;
  }

  public void setDescarga(Double descarga) {
    this.descarga = descarga;
  }

}