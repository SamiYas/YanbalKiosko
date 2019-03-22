package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener usuario token
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerUsuarioTokenDataResponse implements JsonKioskoDataResponse {

  private String usuario;
  private String clave;
  private Long correlativoPais;
  private String pais;

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public Long getCorrelativoPais() {
    return correlativoPais;
  }

  public void setCorrelativoPais(Long correlativoPais) {
    this.correlativoPais = correlativoPais;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }
}
