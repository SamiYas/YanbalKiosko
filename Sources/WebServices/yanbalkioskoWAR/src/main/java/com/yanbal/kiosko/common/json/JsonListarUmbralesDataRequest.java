package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de listar
 * umbrales
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarUmbralesDataRequest {

  private Long pais;

  private String token;

  public Long getPais() {
    return pais;
  }

  public void setPais(Long pais) {
    this.pais = pais;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
