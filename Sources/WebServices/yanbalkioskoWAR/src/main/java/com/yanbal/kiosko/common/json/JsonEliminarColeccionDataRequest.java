package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * eliminar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonEliminarColeccionDataRequest {

  private String token;

  @SerializedName("id_coleccion")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
