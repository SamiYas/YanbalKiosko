package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de mover
 * archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonMoverArchivoDataRequest {

  private String token;
  @SerializedName("id_archivo")
  private Long id;
  @SerializedName("id_coleccion")
  private Long idColeccion;

  public Long getIdColeccion() {
    return idColeccion;
  }

  public void setIdColeccion(Long idColeccion) {
    this.idColeccion = idColeccion;
  }

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

}
