package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonBitacora;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * insertar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonInsertarBitacoraDataRequest {

  private String token;
  @SerializedName("detalle")
  private List<JsonBitacora> listaBitacora;

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token the token to set
   */
  public void setToken(String token) {
    this.token = token;
  }

  public List<JsonBitacora> getListaBitacora() {
    return listaBitacora;
  }

  public void setListaBitacora(List<JsonBitacora> listaBitacora) {
    this.listaBitacora = listaBitacora;
  }

}
