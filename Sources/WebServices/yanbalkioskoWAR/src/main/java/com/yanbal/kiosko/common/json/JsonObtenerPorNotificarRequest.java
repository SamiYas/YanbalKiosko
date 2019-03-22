package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener por notificar
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerPorNotificarRequest {

  private String token;
  private Long pais;

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
