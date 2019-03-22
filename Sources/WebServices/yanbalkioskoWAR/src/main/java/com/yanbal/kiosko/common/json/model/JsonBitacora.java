package com.yanbal.kiosko.common.json.model;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para un objeto Bitacora
 * 
 * @author alex.contreras
 *
 */
public class JsonBitacora {

  private Long correlativoBitacora;
  private String descripcion;
  private String accion;
  private String nombreArchivo;
  private String tamanhoArchivo;
  private String tipoArchivo;
  private String tipoDescarga;
  private String usuario;
  private String plataforma;
  @SerializedName("idDispositivo")
  private String dispositivoIdentificador;
  @SerializedName("fecha")
  private String fechaBitacora;

  /**
   * @return the correlativoBitacora
   */
  public Long getCorrelativoBitacora() {
    return correlativoBitacora;
  }

  /**
   * @param correlativoBitacora the correlativoBitacora to set
   */
  public void setCorrelativoBitacora(Long correlativoBitacora) {
    this.correlativoBitacora = correlativoBitacora;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the accion
   */
  public String getAccion() {
    return accion;
  }

  /**
   * @param accion the accion to set
   */
  public void setAccion(String accion) {
    this.accion = accion;
  }

  /**
   * @return the nombreArchivo
   */
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  /**
   * @param nombreArchivo the nombreArchivo to set
   */
  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  /**
   * @return the tamanhoArchivo
   */
  public String getTamanhoArchivo() {
    return tamanhoArchivo;
  }

  /**
   * @param tamanhoArchivo the tamanhoArchivo to set
   */
  public void setTamanhoArchivo(String tamanhoArchivo) {
    this.tamanhoArchivo = tamanhoArchivo;
  }

  /**
   * @return the tipoArchivo
   */
  public String getTipoArchivo() {
    return tipoArchivo;
  }

  /**
   * @param tipoArchivo the tipoArchivo to set
   */
  public void setTipoArchivo(String tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  /**
   * @return the tipoDescarga
   */
  public String getTipoDescarga() {
    return tipoDescarga;
  }

  /**
   * @param tipoDescarga the tipoDescarga to set
   */
  public void setTipoDescarga(String tipoDescarga) {
    this.tipoDescarga = tipoDescarga;
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
   * @return the idDispositivo
   */
  public String getDispositivoIdentificador() {
    return dispositivoIdentificador;
  }

  /**
   * @param idDispositivo the idDispositivo to set
   */
  public void setDispositivoIdentificador(String dispositivoIdentificador) {
    this.dispositivoIdentificador = dispositivoIdentificador;
  }

  /**
   * @return the fecha
   */
  public String getFechaBitacora() {
    return fechaBitacora;
  }

  /**
   * @param fechaBitacora the fecha to set
   */
  public void setFechaBitacora(String fechaBitacora) {
    this.fechaBitacora = fechaBitacora;
  }

}
