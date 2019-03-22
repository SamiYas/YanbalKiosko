package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerArchivoDataRequest {

  private String token;
  @SerializedName("id_archivo")
  private Long id;

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
