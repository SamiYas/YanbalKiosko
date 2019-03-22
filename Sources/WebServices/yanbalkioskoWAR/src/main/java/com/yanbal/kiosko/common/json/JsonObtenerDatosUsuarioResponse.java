package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener datos usuario
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerDatosUsuarioResponse implements JsonKioskoDataResponse {

  private String flagValidacion;
  private String estado;
  private String perfil;

  public String getFlagValidacion() {
    return flagValidacion;
  }

  public void setFlagValidacion(String flagValidacion) {
    this.flagValidacion = flagValidacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getPerfil() {
    return perfil;
  }

  public void setPerfil(String perfil) {
    this.perfil = perfil;
  }

}
