package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * cerrar sesion movil
 * 
 * @author alex.contreras
 * 
 */
public class JsonCerrarSesionMovilDataRequest {
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
