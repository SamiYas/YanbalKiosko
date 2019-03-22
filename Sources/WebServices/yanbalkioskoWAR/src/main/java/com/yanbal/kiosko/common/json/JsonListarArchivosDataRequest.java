package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de listar
 * archivos
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarArchivosDataRequest {

  private String token;
  @SerializedName("id_coleccion")
  private Long idColeccion;
  private Long pais;
  private String nombre;
  private String descripcion;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getIdColeccion() {
    return idColeccion;
  }

  public void setIdColeccion(Long idColeccion) {
    this.idColeccion = idColeccion;
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

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
