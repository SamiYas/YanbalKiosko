package com.yanbal.kiosko.common.json;

import java.util.List;
import com.yanbal.kiosko.common.json.model.JsonRol;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * listar roles
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarRolesDataResponse implements JsonKioskoDataResponse {

  private List<JsonRol> roles;

  public List<JsonRol> getRoles() {
    return roles;
  }

  public void setRoles(List<JsonRol> roles) {
    this.roles = roles;
  }
}
