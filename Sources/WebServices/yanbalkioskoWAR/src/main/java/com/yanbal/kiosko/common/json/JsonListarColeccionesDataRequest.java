package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de listar
 * colecciones
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarColeccionesDataRequest {

  private String token;

  @SerializedName("id_coleccion_padre")
  private Long idPadre;
  private Long pais;

  public Long getIdPadre() {
    return idPadre;
  }

  public void setIdPadre(Long idPadre) {
    this.idPadre = idPadre;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getPais() {
    return pais;
  }

  public void setPais(Long pais) {
    this.pais = pais;
  }

}
