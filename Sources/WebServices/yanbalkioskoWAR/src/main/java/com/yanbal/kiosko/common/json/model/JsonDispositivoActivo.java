package com.yanbal.kiosko.common.json.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para un objeto Coleccion
 * 
 * @author lennin.davila
 *
 */
public class JsonDispositivoActivo {
  @SerializedName("correlativoDispositivoActivo")
  private Long correlativoDispositivoActivo;
  private Long correlativoPais;
  private String usuario;
  private String clave;
  private String notificacionIdentificador;
  private String dispositivoSis;
  private String dispositivoIdentificador;
  private String token;
  private Date fechaRegistro;
  private Date fechaUltimaSinc;

  /**
   * @return the correlativoDispositivoActivo
   */
  public Long getCorrelativoDispositivoActivo() {
    return correlativoDispositivoActivo;
  }

  /**
   * @param correlativoDispositivoActivo the correlativoDispositivoActivo to set
   */
  public void setCorrelativoDispositivoActivo(Long correlativoDispositivoActivo) {
    this.correlativoDispositivoActivo = correlativoDispositivoActivo;
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
   * @return the notificacionIdentificador
   */
  public String getNotificacionIdentificador() {
    return notificacionIdentificador;
  }

  /**
   * @param notificacionIdentificador the notificacionIdentificador to set
   */
  public void setNotificacionIdentificador(String notificacionIdentificador) {
    this.notificacionIdentificador = notificacionIdentificador;
  }

  /**
   * @return the dispositivoSis
   */
  public String getDispositivoSis() {
    return dispositivoSis;
  }

  /**
   * @param dispositivoSis the dispositivoSis to set
   */
  public void setDispositivoSis(String dispositivoSis) {
    this.dispositivoSis = dispositivoSis;
  }

  /**
   * @return the dispositivoIdentificador
   */
  public String getDispositivoIdentificador() {
    return dispositivoIdentificador;
  }

  /**
   * @param dispositivoIdentificador the dispositivoIdentificador to set
   */
  public void setDispositivoIdentificador(String dispositivoIdentificador) {
    this.dispositivoIdentificador = dispositivoIdentificador;
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
   * @return the fechaUltimaSinc
   */
  public Date getFechaUltimaSinc() {
    return fechaUltimaSinc;
  }

  /**
   * @param fechaUltimaSinc the fechaUltimaSinc to set
   */
  public void setFechaUltimaSinc(Date fechaUltimaSinc) {
    this.fechaUltimaSinc = fechaUltimaSinc;
  }
}
