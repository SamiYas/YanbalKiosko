package com.yanbal.kiosko.entities;

import java.util.Date;

import com.yanbal.kiosko.common.core.KioskoEntidad;
/**
 * Clase pantilla de la tabla KSK_ARCHIVO
 * 
 * @author alex.contreras
 * 
 */
public class Archivo extends KioskoEntidad {

  private static final long serialVersionUID = 1L;

  private Long correlativoArchivo;
  private Long correlativoColeccion;
  private Long correlativoPais;
  private Long correlativoRol;
  private String nombre;
  private String descripcion;
  private String tamanho;
  private String extension;
  private Integer nroOrden;
  private Integer descargable;
  private Integer destacado;
  private String rutaImgprevia;
  private String rutaArchivo;
  private Date fechaInicio;
  private Date fechaCaducidad;
  private Date fechaSincronizacion;
  private Integer eliminado;
  private Integer notificado;
  private String usuarioModifica;
  private String usuarioRegistra;
  private String estado;
  private String roles;
  private String pais;

  /**
   * @return the correlativoArchivo
   */
  public Long getCorrelativoArchivo() {
    return correlativoArchivo;
  }

  /**
   * @param correlativoArchivo the correlativoArchivo to set
   */
  public void setCorrelativoArchivo(Long correlativoArchivo) {
    this.correlativoArchivo = correlativoArchivo;
  }

  /**
   * @return the correlativoColeccion
   */
  public Long getCorrelativoColeccion() {
    return correlativoColeccion;
  }

  /**
   * @param correlativoColeccion the correlativoColeccion to set
   */
  public void setCorrelativoColeccion(Long correlativoColeccion) {
    this.correlativoColeccion = correlativoColeccion;
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

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
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
   * @return the tamanho
   */
  public String getTamanho() {
    return tamanho;
  }

  /**
   * @param tamanho the tamanho to set
   */
  public void setTamanho(String tamanho) {
    this.tamanho = tamanho;
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
   * @return the nroOrden
   */
  public Integer getNroOrden() {
    return nroOrden;
  }

  /**
   * @param nroOrden the nroOrden to set
   */
  public void setNroOrden(Integer nroOrden) {
    this.nroOrden = nroOrden;
  }

  /**
   * @return the descargable
   */
  public Integer getDescargable() {
    return descargable;
  }

  /**
   * @param descargable the descargable to set
   */
  public void setDescargable(Integer descargable) {
    this.descargable = descargable;
  }

  /**
   * @return the destacado
   */
  public Integer getDestacado() {
    return destacado;
  }

  /**
   * @param destacado the destacado to set
   */
  public void setDestacado(Integer destacado) {
    this.destacado = destacado;
  }

  /**
   * @return the rutaImgprevia
   */
  public String getRutaImgprevia() {
    return rutaImgprevia;
  }

  /**
   * @param rutaImgprevia the rutaImgprevia to set
   */
  public void setRutaImgprevia(String rutaImgprevia) {
    this.rutaImgprevia = rutaImgprevia;
  }

  /**
   * @return the rutaArchivo
   */
  public String getRutaArchivo() {
    return rutaArchivo;
  }

  /**
   * @param rutaArchivo the rutaArchivo to set
   */
  public void setRutaArchivo(String rutaArchivo) {
    this.rutaArchivo = rutaArchivo;
  }

  /**
   * @return the fechaInicio
   */
  public Date getFechaInicio() {
    return fechaInicio;
  }

  /**
   * @param fechaInicio the fechaInicio to set
   */
  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  /**
   * @return the fechaCaducidad
   */
  public Date getFechaCaducidad() {
    return fechaCaducidad;
  }

  /**
   * @param fechaCaducidad the fechaCaducidad to set
   */
  public void setFechaCaducidad(Date fechaCaducidad) {
    this.fechaCaducidad = fechaCaducidad;
  }

  /**
   * @return the fechaSincronizacion
   */
  public Date getFechaSincronizacion() {
    return fechaSincronizacion;
  }

  /**
   * @param fechaSincronizacion the fechaSincronizacion to set
   */
  public void setFechaSincronizacion(Date fechaSincronizacion) {
    this.fechaSincronizacion = fechaSincronizacion;
  }

  /**
   * @return the eliminado
   */
  public Integer getEliminado() {
    return eliminado;
  }

  /**
   * @param eliminado the eliminado to set
   */
  public void setEliminado(Integer eliminado) {
    this.eliminado = eliminado;
  }

  /**
   * @return the notificado
   */
  public Integer getNotificado() {
    return notificado;
  }

  /**
   * @param notificado the notificado to set
   */
  public void setNotificado(Integer notificado) {
    this.notificado = notificado;
  }

  public String getUsuarioModifica() {
    return usuarioModifica;
  }

  public void setUsuarioModifica(String usuarioModifica) {
    this.usuarioModifica = usuarioModifica;
  }

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }
}
