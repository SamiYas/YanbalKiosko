package com.yanbal.lib.corp.entities;

import java.util.Date;

/**
 * Clase Bean de push de envio.
 * 
 * @author lennin.davila
 *
 */
public class PushEnvio {
  private Long correlativoPushEnvio;
  private String mensaje;
  private Date fechaHoraInicio;
  private Date fechaHoraFin;
  private Long exitosos;
  private Long errores;
  private String motivoError;
  private String plataforma;
  private String pais;

  /**
   * @return the correlativoPushEnvio
   */
  public Long getCorrelativoPushEnvio() {
    return correlativoPushEnvio;
  }

  /**
   * @param correlativoPushEnvio the correlativoPushEnvio to set
   */
  public void setCorrelativoPushEnvio(Long correlativoPushEnvio) {
    this.correlativoPushEnvio = correlativoPushEnvio;
  }

  /**
   * @return the mensaje
   */
  public String getMensaje() {
    return mensaje;
  }

  /**
   * @param mensaje the mensaje to set
   */
  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  /**
   * @return the fechaHoraInicio
   */
  public Date getFechaHoraInicio() {
    return fechaHoraInicio;
  }

  /**
   * @param fechaHoraInicio the fechaHoraInicio to set
   */
  public void setFechaHoraInicio(Date fechaHoraInicio) {
    this.fechaHoraInicio = fechaHoraInicio;
  }

  /**
   * @return the fechaHoraFin
   */
  public Date getFechaHoraFin() {
    return fechaHoraFin;
  }

  /**
   * @param fechaHoraFin the fechaHoraFin to set
   */
  public void setFechaHoraFin(Date fechaHoraFin) {
    this.fechaHoraFin = fechaHoraFin;
  }

  /**
   * @return the exitosos
   */
  public Long getExitosos() {
    return exitosos;
  }

  /**
   * @param exitosos the exitosos to set
   */
  public void setExitosos(Long exitosos) {
    this.exitosos = exitosos;
  }

  /**
   * @return the errores
   */
  public Long getErrores() {
    return errores;
  }

  /**
   * @param errores the errores to set
   */
  public void setErrores(Long errores) {
    this.errores = errores;
  }

  /**
   * @return the motivoError
   */
  public String getMotivoError() {
    return motivoError;
  }

  /**
   * @param motivoError the motivoError to set
   */
  public void setMotivoError(String motivoError) {
    this.motivoError = motivoError;
  }

  /**
   * @return the plataforma
   */
  public String getPlataforma() {
    return plataforma;
  }

  /**
   * @param plataforma the plataforma to set
   */
  public void setPlataforma(String plataforma) {
    this.plataforma = plataforma;
  }

  /**
   * @return the pais
   */
  public String getPais() {
    return pais;
  }

  /**
   * @param pais the pais to set
   */
  public void setPais(String pais) {
    this.pais = pais;
  }
}
