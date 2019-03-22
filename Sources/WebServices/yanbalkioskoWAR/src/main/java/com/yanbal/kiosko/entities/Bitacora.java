package com.yanbal.kiosko.entities;

import java.util.Date;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase plantilla de la tabla KSK_BITACORA
 * 
 * @author lennin.davila
 * 
 */
public class Bitacora extends KioskoEntidad {

  private static final long serialVersionUID = 6852559018147050601L;

  private Long correlativoBitacora;
  private String descripcion;
  private String accion;
  private String nombreArchivo;
  private String tamanhoArchivo;
  private String tipoArchivo;
  private String tipoDescarga;
  private String usuario;
  private String plataforma;
  private String dispositivoIdentificador;
  private Date fecha;

  public Bitacora() {

  }

  /**
   * Constructor principal de la clase
   * 
   * @param descripcion descripcion de la bitacora
   * @param accion accion realizada
   * @param nombreArchivo nombre del archivo
   * @param tamanhoArchivo tamanho del archivo
   * @param tipoArchivo tipo de archivo
   * @param tipoDescarga tipo de desacarga
   * @param usuario usuario
   * @param plataforma plataforma
   * @param dispositivoIdentificador ID del dispositivo
   * @param fecha fecha
   */
  public Bitacora(String descripcion, String accion, String nombreArchivo, String tamanhoArchivo,
      String tipoArchivo, String tipoDescarga, String usuario) {
    super();
    this.descripcion = descripcion;
    this.accion = accion;
    this.nombreArchivo = nombreArchivo;
    this.tamanhoArchivo = tamanhoArchivo;
    this.tipoArchivo = tipoArchivo;
    this.tipoDescarga = tipoDescarga;
    this.usuario = usuario;
  }

  /**
   * Obtiene un numero correlativo de la bitacora
   * 
   * @return numero correlativo de la bitacora
   */
  public Long getCorrelativoBitacora() {
    return correlativoBitacora;
  }

  /**
   * Establece un numero correlativo a la bitacora
   * 
   * @param correlativoBitacora numero correlativo a la bitacora
   */
  public void setCorrelativoBitacora(Long correlativoBitacora) {
    this.correlativoBitacora = correlativoBitacora;
  }

  /**
   * Obtiene la descripcion de la bitacora
   * 
   * @return descripcion de la bitacora
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * Establece la descripcion de la bitacora
   * 
   * @param descripcion de la bitacora
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * Obtiene la accion
   * 
   * @return accion
   */
  public String getAccion() {
    return accion;
  }

  /**
   * Establece la accion
   * 
   * @param accion
   */
  public void setAccion(String accion) {
    this.accion = accion;
  }

  /**
   * Obtiene el nombre del archivo
   * 
   * @return nombre del archivo
   */
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  /**
   * Establece el nombre del archivo
   * 
   * @param nombreArchivo nombre del archivo
   */
  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  /**
   * Obtiene el tamanho del archivo
   * 
   * @return tamanho del archivo
   */
  public String getTamanhoArchivo() {
    return tamanhoArchivo;
  }

  /**
   * Establece el tamanho del archivo
   * 
   * @param tamanhoArchivo tamanho del archivo
   */
  public void setTamanhoArchivo(String tamanhoArchivo) {
    this.tamanhoArchivo = tamanhoArchivo;
  }

  /**
   * Obtiene el tipo de archivo
   * 
   * @return tipo de archivo
   */
  public String getTipoArchivo() {
    return tipoArchivo;
  }

  /**
   * Establece el tipo de archivo
   * 
   * @param tipoArchivo tipo de archivo
   */
  public void setTipoArchivo(String tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  /**
   * Obtiene el tipo de descarga
   * 
   * @return tipo de descarga
   */
  public String getTipoDescarga() {
    return tipoDescarga;
  }

  /**
   * Establece el tipo de descarga
   * 
   * @param tipoDescarga tipo de descarga
   */
  public void setTipoDescarga(String tipoDescarga) {
    this.tipoDescarga = tipoDescarga;
  }

  /**
   * Obtiene el usuario
   * 
   * @return usuario establecido
   */

  public String getUsuario() {
    return usuario;
  }

  /**
   * Establece el usuario
   * 
   * @param usuario usuario a establecer
   */
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  /**
   * Obtinene la plataforma
   * 
   * @return plataforma
   */
  public String getPlataforma() {
    return plataforma;
  }

  /**
   * Establece la plataforma
   * 
   * @param plataforma plataforma
   */
  public void setPlataforma(String plataforma) {
    this.plataforma = plataforma;
  }

  /**
   * Obtiene el ID del dispositivo
   * 
   * @return ID del dispositivo
   */
  public String getDispositivoIdentificador() {
    return dispositivoIdentificador;
  }

  /**
   * Establece el ID del dispositivo
   * 
   * @param idDispositivo ID del dispositivo
   */
  public void setDispositivoIdentificador(String dispositivoIdentificador) {
    this.dispositivoIdentificador = dispositivoIdentificador;
  }

  /**
   * Obtiene la fecha
   * 
   * @return fecha
   */
  public Date getFecha() {
    return fecha;
  }

  /**
   * Establece la fecha
   * 
   * @param fecha fecha
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
}
