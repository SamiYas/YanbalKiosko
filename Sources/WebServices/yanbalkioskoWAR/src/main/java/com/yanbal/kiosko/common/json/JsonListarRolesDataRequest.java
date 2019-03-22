package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de 
 * listar roles
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarRolesDataRequest {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
