package com.yanbal.kiosko.common.json;

import java.util.List;

import com.yanbal.kiosko.common.json.model.JsonPais;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * listar paises
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarPaisesDataResponse implements JsonKioskoDataResponse {

  private List<JsonPais> paises;

  public List<JsonPais> getPaises() {
    return paises;
  }

  public void setPaises(List<JsonPais> paises) {
    this.paises = paises;
  }
}
