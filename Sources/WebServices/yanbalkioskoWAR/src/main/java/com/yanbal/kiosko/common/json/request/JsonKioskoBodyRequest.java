package com.yanbal.kiosko.common.json.request;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura del cuerpo de los request JSON que se reciben en los
 * servicios
 * 
 * @author alex.contreras
 * 
 * @param <D> define el tipo de contenido para el Request
 */
public class JsonKioskoBodyRequest<D> {

  @SerializedName("Request")
  private D data;

  public D getData() {
    return data;
  }

  public void setData(D data) {
    this.data = data;
  }

}
