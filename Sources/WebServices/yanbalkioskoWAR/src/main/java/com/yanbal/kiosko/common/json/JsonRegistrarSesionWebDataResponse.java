package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.model.JsonResultado;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * registrar sesion web
 * 
 * @author alex.contreras
 * 
 */
public class JsonRegistrarSesionWebDataResponse implements JsonKioskoDataResponse {

  private String token;
  private String usuario;
  private JsonResultado resultado;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public JsonResultado getResultado() {
    return resultado;
  }

  public void setResultado(JsonResultado resultado) {
    this.resultado = resultado;
  }

}
