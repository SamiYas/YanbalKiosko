package com.yanbal.kiosko.interfaces.rest.servicios.json;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo de autenticacion del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonIntegracionWSResp {

  @SerializedName("Cabecera")
  private JsonCabeceraWSResp cabecera;

  @SerializedName("Detalle")
  private JsonDetalleWSResp Detalle;

  /**
   * @return the cabecera
   */
  public JsonCabeceraWSResp getCabecera() {
    return cabecera;
  }

  /**
   * @param cabecera the cabecera to set
   */
  public void setCabecera(JsonCabeceraWSResp cabecera) {
    this.cabecera = cabecera;
  }

  /**
   * @return the detalle
   */
  public JsonDetalleWSResp getDetalle() {
    return Detalle;
  }

  /**
   * @param detalle the detalle to set
   */
  public void setDetalle(JsonDetalleWSResp detalle) {
    Detalle = detalle;
  }
}
