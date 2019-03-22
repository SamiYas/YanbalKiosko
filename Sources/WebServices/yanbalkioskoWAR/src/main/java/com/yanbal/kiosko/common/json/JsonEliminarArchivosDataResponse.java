package com.yanbal.kiosko.common.json;

import java.util.List;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * eliminar archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonEliminarArchivosDataResponse implements JsonKioskoDataResponse {

  private String resultado;
  private List<String> archivos;

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public List<String> getArchivos() {
    return archivos;
  }

  public void setArchivos(List<String> archivos) {
    this.archivos = archivos;
  }

}
