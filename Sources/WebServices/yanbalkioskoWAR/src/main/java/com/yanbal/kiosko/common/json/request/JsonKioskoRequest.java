package com.yanbal.kiosko.common.json.request;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura del request JSON que se reciben en los servicios
 * 
 * @author alex.contreras
 * 
 * @param <D> define el tipo de contenido para el Request
 */
public class JsonKioskoRequest<D> {

  @SerializedName("Header")
  private JsonKioskoHeaderRequest header;

  @SerializedName("Body")
  private JsonKioskoBodyRequest<D> body;

  public JsonKioskoHeaderRequest getHeader() {
    return header;
  }

  public JsonKioskoBodyRequest<D> getBody() {
    return body;
  }

  public void setHeader(JsonKioskoHeaderRequest header) {
    this.header = header;
  }

  public void setBody(JsonKioskoBodyRequest<D> body) {
    this.body = body;
  }
}
