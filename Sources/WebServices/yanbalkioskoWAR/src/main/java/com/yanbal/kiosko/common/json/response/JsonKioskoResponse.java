package com.yanbal.kiosko.common.json.response;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura del response JSON que se devuelve en los servicios
 * 
 * @author alex.contreras
 * 
 */
public class JsonKioskoResponse {

  @SerializedName("Header")
  private JsonKioskoHeaderResponse header;

  @SerializedName("Body")
  private JsonKioskoBodyResponse body;

  public JsonKioskoHeaderResponse getHeader() {
    return header;
  }

  public JsonKioskoBodyResponse getBody() {
    return body;
  }

  public void setHeader(JsonKioskoHeaderResponse header) {
    this.header = header;
  }

  public void setBody(JsonKioskoBodyResponse body) {
    this.body = body;
  }

}
