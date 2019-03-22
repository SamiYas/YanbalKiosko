package com.yanbal.kiosko.interfaces.rest.servicios.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo de paises del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonDatosWSResp {
  @SerializedName("Paises")
  private List<PaisYanbal> paises;

  /**
   * @return the paises
   */
  public List<PaisYanbal> getPaises() {
    return paises;
  }

  /**
   * @param paises the paises to set
   */
  public void setPaises(List<PaisYanbal> paises) {
    this.paises = paises;
  }
}
