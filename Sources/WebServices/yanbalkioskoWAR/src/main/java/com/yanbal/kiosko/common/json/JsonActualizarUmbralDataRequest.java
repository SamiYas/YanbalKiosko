package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * actualizar umbral
 * 
 * @author alex.contreras
 * 
 */
public class JsonActualizarUmbralDataRequest {

  private String token;
  private Long id;
  private Long rol;
  private String extension;
  private String descripcion;
  private Double carga;
  private Double descarga;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getRol() {
    return rol;
  }

  public void setRol(Long rol) {
    this.rol = rol;
  }

  public Long getId() {
    return id;
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
