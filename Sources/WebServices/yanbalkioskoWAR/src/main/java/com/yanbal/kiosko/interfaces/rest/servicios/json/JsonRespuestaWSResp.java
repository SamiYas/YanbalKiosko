package com.yanbal.kiosko.interfaces.rest.servicios.json;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo de respuesta del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonRespuestaWSResp {

  @SerializedName("CodigoRespuesta")
  private String codigoRespuesta;
  @SerializedName("MensajeRespuesta")
  private String mensajeRespuesta;
  @SerializedName("Datos")
  private JsonDatosWSResp datos;

  /**
   * @return the codigoRespuesta
   */
  public String getCodigoRespuesta() {
    return codigoRespuesta;
  }

  /**
   * @param codigoRespuesta the codigoRespuesta to set
   */
  public void setCodigoRespuesta(String codigoRespuesta) {
    this.codigoRespuesta = codigoRespuesta;
  }

  /**
   * @return the mensajeRespuesta
   */
  public String getMensajeRespuesta() {
    return mensajeRespuesta;
  }

  /**
   * @param mensajeRespuesta the mensajeRespuesta to set
   */
  public void setMensajeRespuesta(String mensajeRespuesta) {
    this.mensajeRespuesta = mensajeRespuesta;
  }

  /**
   * @return the datos
   */
  public JsonDatosWSResp getDatos() {
    return datos;
  }

  /**
   * @param datos the datos to set
   */
  public void setDatos(JsonDatosWSResp datos) {
    this.datos = datos;
  }
}
