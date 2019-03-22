package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener usuario token
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerUsuarioTokenDataRequest {
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
