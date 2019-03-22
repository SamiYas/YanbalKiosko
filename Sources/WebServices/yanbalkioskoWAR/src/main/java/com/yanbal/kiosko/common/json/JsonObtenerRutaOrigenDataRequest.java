package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener ruta
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerRutaOrigenDataRequest {

  private String token;
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
