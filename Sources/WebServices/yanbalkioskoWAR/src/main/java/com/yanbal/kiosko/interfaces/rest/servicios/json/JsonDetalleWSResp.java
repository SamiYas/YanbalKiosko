package com.yanbal.kiosko.interfaces.rest.servicios.json;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo de autenticacion del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonDetalleWSResp {
  @SerializedName("Respuesta")
  private JsonRespuestaWSResp respuesta;

  /**
   * @return the respuesta
   */
  public JsonRespuestaWSResp getRespuesta() {
    return respuesta;
  }

  /**
   * @param respuesta the respuesta to set
   */
  public void setRespuesta(JsonRespuestaWSResp respuesta) {
    this.respuesta = respuesta;
  }
}
