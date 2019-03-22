package com.yanbal.kiosko.common.json.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para un objeto SesionWeb
 * 
 * @author lennin.davila
 *
 */
public class JsonSesionWeb {
  @SerializedName("correlativoSesionWeb")
  private Long correlativoSesionWeb;
  private Long correlativoPais;
  private String usuario;
  private String clave;
  private String token;
  private Date fechaRegistro;
  private Long correlativoRol;

  /**
   * @return the correlativoSesionWeb
   */
  public Long getCorrelativoSesionWeb() {
    return correlativoSesionWeb;
  }

  /**
   * @param correlativoSesionWeb the correlativoSesionWeb to set
   */
  public void setCorrelativoSesionWeb(Long correlativoSesionWeb) {
    this.correlativoSesionWeb = correlativoSesionWeb;
  }

  /**
   * @return the correlativoPais
   */
  public Long getCorrelativoPais() {
    return correlativoPais;
  }

  /**
   * @param correlativoPais the correlativoPais to set
   */
  public void setCorrelativoPais(Long correlativoPais) {
    this.correlativoPais = correlativoPais;
  }

  /**
   * @return the usuario
   */
  public String getUsuario() {
    return usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the clave
   */
  public String getClave() {
    return clave;
  }

  /**
   * @param clave the clave to set
   */
  public void setClave(String clave) {
    this.clave = clave;
  }

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

  /**
   * @return the fechaRegistro
   */
  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  /**
   * @param fechaRegistro the fechaRegistro to set
   */
  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  /**
   * @return the correlativoRol
   */
  public Long getCorrelativoRol() {
    return correlativoRol;
  }

  /**
   * @param correlativoRol the correlativoRol to set
   */
  public void setCorrelativoRol(Long correlativoRol) {
    this.correlativoRol = correlativoRol;
  }
}
