package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * eliminar archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonEliminarArchivosDataRequest {

  private String token;
  @SerializedName("ids_archivo")
  private List<Long> listaArchivos;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public List<Long> getListaArchivos() {
    return listaArchivos;
  }

  public void setListaArchivos(List<Long> listaArchivos) {
    this.listaArchivos = listaArchivos;
  }

}
