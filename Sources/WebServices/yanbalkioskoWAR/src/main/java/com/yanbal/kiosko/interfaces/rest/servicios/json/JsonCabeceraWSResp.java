package com.yanbal.kiosko.interfaces.rest.servicios.json;

import com.google.gson.annotations.SerializedName;
/**
 * Clase plantilla para el parseo de autenticacion del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class JsonCabeceraWSResp {
  @SerializedName("CodigoInterfaz")
  private String codigoInterfaz;
  @SerializedName("UsuarioAplicacion")
  private String usuarioAplicacion;
  @SerializedName("CodigoAplicacion")
  private String codigoAplicacion;
  @SerializedName("CodigoPais")
  private String codigoPais;
  @SerializedName("CodigosPaisOD")
  private Object codigosPaisOD;
  @SerializedName("IdTransaccion")
  private String idTransaccion;

  /**
   * @return the codigoInterfaz
   */
  public String getCodigoInterfaz() {
    return codigoInterfaz;
  }

  /**
   * @param codigoInterfaz the codigoInterfaz to set
   */
  public void setCodigoInterfaz(String codigoInterfaz) {
    this.codigoInterfaz = codigoInterfaz;
  }

  /**
   * @return the usuarioAplicacion
   */
  public String getUsuarioAplicacion() {
    return usuarioAplicacion;
  }

  /**
   * @param usuarioAplicacion the usuarioAplicacion to set
   */
  public void setUsuarioAplicacion(String usuarioAplicacion) {
    this.usuarioAplicacion = usuarioAplicacion;
  }

  /**
   * @return the codigoAplicacion
   */
  public String getCodigoAplicacion() {
    return codigoAplicacion;
  }

  /**
   * @param codigoAplicacion the codigoAplicacion to set
   */
  public void setCodigoAplicacion(String codigoAplicacion) {
    this.codigoAplicacion = codigoAplicacion;
  }

  /**
   * @return the codigoPais
   */
  public String getCodigoPais() {
    return codigoPais;
  }

  /**
   * @param codigoPais the codigoPais to set
   */
  public void setCodigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
  }

  /**
   * @return the codigosPaisOD
   */
  public Object getCodigosPaisOD() {
    return codigosPaisOD;
  }

  /**
   * @param codigosPaisOD the codigosPaisOD to set
   */
  public void setCodigosPaisOD(Object codigosPaisOD) {
    this.codigosPaisOD = codigosPaisOD;
  }

  /**
   * @return the idTransaccion
   */
  public String getIdTransaccion() {
    return idTransaccion;
  }

  /**
   * @param idTransaccion the idTransaccion to set
   */
  public void setIdTransaccion(String idTransaccion) {
    this.idTransaccion = idTransaccion;
  }
}
