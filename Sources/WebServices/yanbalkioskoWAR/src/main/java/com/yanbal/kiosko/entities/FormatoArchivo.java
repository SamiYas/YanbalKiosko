package com.yanbal.kiosko.entities;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase plantilla de la tabla KSK_FORMATO_ARCHIVO
 * 
 * @author alex.contreras
 *
 */
public class FormatoArchivo extends KioskoEntidad {

  private static final long serialVersionUID = 1L;
  private Long correlativoFormatoArchivo;
  private Long correlativoRol;
  private String extension;
  private String descripcion;
  private Double descarga;
  private Double carga;
  private String usuarioModifica;

  public FormatoArchivo() {    
  }

  public FormatoArchivo(Long correlativoFormatoArchivo, Long correlativoRol, String extension,
      String descripcion, Double descarga, Double carga, String usuarioModifica) {
    super();
    this.correlativoFormatoArchivo = correlativoFormatoArchivo;
    this.correlativoRol = correlativoRol;
    this.extension = extension;
    this.descripcion = descripcion;
    this.descarga = descarga;
    this.carga = carga;
    this.usuarioModifica = usuarioModifica;
  }

  /**
   * @return the correlativoFormatoArchivo
   */
  public Long getCorrelativoFormatoArchivo() {
    return correlativoFormatoArchivo;
  }

  /**
   * @param correlativoFormatoArchivo the correlativoFormatoArchivo to set
   */
  public void setCorrelativoFormatoArchivo(Long correlativoFormatoArchivo) {
    this.correlativoFormatoArchivo = correlativoFormatoArchivo;
  }

  /**
   * @return the correlativoRol
   */
  public Long getCorrelativoRol() {
    return correlativoRol;
  }

  /**
   * @param correlativoRol the correlativoFormatoArchivo to set
   */
  public void setCorrelativoRol(Long correlativoRol) {
    this.correlativoRol = correlativoRol;
  }

  /**
   * @return the extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * @param extension the extension to set
   */
  public void setExtension(String extension) {
    this.extension = extension;
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
   * @return the descarga
   */
  public Double getDescarga() {
    return descarga;
  }

  /**
   * @param descarga the descarga to set
   */
  public void setDescarga(Double descarga) {
    this.descarga = descarga;
  }

  /**
   * @return the carga
   */
  public Double getCarga() {
    return carga;
  }

  /**
   * @param carga the carga to set
   */
  public void setCarga(Double carga) {
    this.carga = carga;
  }

  /**
   * Obtiene el usuario que realizo la modificacion
   * 
   * @return usuario
   */
  public String getUsuarioModifica() {
    return usuarioModifica;
  }

  /**
   * Establece el usuario que realizo la modificacion
   * 
   * @param usuario
   */
  public void setUsuarioModifica(String usuarioModifica) {
    this.usuarioModifica = usuarioModifica;
  }
}
