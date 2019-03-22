package com.yanbal.lib.corp.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Clase bean que con los atributos para un reporte de cada envio
 * 
 * @author lennin.davila
 *
 */
public final class MultiResultadoPush implements Serializable {
  private int success;
  private int failure;
  private transient List<String> tokenFails;
  private String mensaje;
  private String plataforma;

  /**
   * @return the success
   */
  public int getSuccess() {
    return success;
  }

  /**
   * @param success the success to set
   */
  public void setSuccess(int success) {
    this.success = success;
  }

  /**
   * @return the failure
   */
  public int getFailure() {
    return failure;
  }

  /**
   * @param failure the failure to set
   */
  public void setFailure(int failure) {
    this.failure = failure;
  }

  /**
   * @return the tokenFails
   */
  public List<String> getTokenFails() {
    return tokenFails;
  }

  /**
   * @param tokenFails the tokenFails to set
   */
  public void setTokenFails(List<String> tokenFails) {
    this.tokenFails = tokenFails;
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
}
