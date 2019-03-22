package com.yanbal.kiosko.interfaces.rest.servicios.json;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo  del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonYanbalRequest {

  @SerializedName("IntegracionWSResp")
  private JsonIntegracionWSResp integracionWSResp;

  /**
   * @return the integracionWSResp
   */
  public JsonIntegracionWSResp getIntegracionWSResp() {
    return integracionWSResp;
  }

  /**
   * @param integracionWSResp the integracionWSResp to set
   */
  public void setIntegracionWSResp(JsonIntegracionWSResp integracionWSResp) {
    this.integracionWSResp = integracionWSResp;
  }

}
