package com.yanbal.kiosko.entities;

import java.util.Date;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase plantilla de la tabla KSK_COLECCION
 * 
 * @author alex.contreras
 *
 */
public class Coleccion extends KioskoEntidad {

  private static final long serialVersionUID = 1L;
  private Long correlativoColeccion;
  private Long correlativoColeccionPadre;
  private String nombreColeccionPadre;
  private Long correlativoPais;
  private Long correlativoRol;
  private String nombre;
  private Integer nivel;
  private Integer nroOrden;
  private String descripcion;
  private String color;
  private Date fechaSincronizacion;
  private String usuarioRegistra;
  private String usuarioModifica;
  private Integer cantidadColeccionesHijas;

  public Coleccion() {    
  }

  public Coleccion(Long correlativoColeccionPadre, Long correlativoPais, Long correlativoRol,
      String nombre, Integer nivel, Integer nroOrden, String descripcion) {
    super();
    this.correlativoColeccionPadre = correlativoColeccionPadre;
    this.correlativoPais = correlativoPais;
    this.correlativoRol = correlativoRol;
    this.nombre = nombre;
    this.nivel = nivel;
    this.nroOrden = nroOrden;
    this.descripcion = descripcion;
  }

  public Coleccion(Long correlativoColeccion, Long correlativoColeccionPadre, String nombre,
      Integer nivel, Integer nroOrden, String descripcion, String color) {
    super();
    this.correlativoColeccion = correlativoColeccion;
    this.correlativoColeccionPadre = correlativoColeccionPadre;
    this.nombre = nombre;
    this.nivel = nivel;
    this.nroOrden = nroOrden;
    this.descripcion = descripcion;
    this.color = color;
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
   * @return the correlativoColeccionPadre
   */
  public Long getCorrelativoColeccionPadre() {
    return correlativoColeccionPadre;
  }

  /**
   * @param correlativoColeccionPadre the correlativoColeccionPadre to set
   */
  public void setCorrelativoColeccionPadre(Long correlativoColeccionPadre) {
    this.correlativoColeccionPadre = correlativoColeccionPadre;
  }

  /**
   * @return the nombreColeccionPadre
   */
  public String getNombreColeccionPadre() {
    return nombreColeccionPadre;
  }

  /**
   * @param nombreColeccionPadre the nombreColeccionPadre to set
   */
  public void setNombreColeccionPadre(String nombreColeccionPadre) {
    this.nombreColeccionPadre = nombreColeccionPadre;
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
   * @return the nivel
   */
  public Integer getNivel() {
    return nivel;
  }

  /**
   * @param nivel the nivel to set
   */
  public void setNivel(Integer nivel) {
    this.nivel = nivel;
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
   * @return the color
   */
  public String getColor() {
    return color;
  }

  /**
   * @param color the color to set
   */
  public void setColor(String color) {
    this.color = color;
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

  public String getUsuarioRegistra() {
    return usuarioRegistra;
  }

  public void setUsuarioRegistra(String usuarioRegistra) {
    this.usuarioRegistra = usuarioRegistra;
  }

  public String getUsuarioModifica() {
    return usuarioModifica;
  }

  public void setUsuarioModifica(String usuarioModifica) {
    this.usuarioModifica = usuarioModifica;
  }

  public Integer getCantidadColeccionesHijas() {
    return cantidadColeccionesHijas;
  }

  public void setCantidadColeccionesHijas(Integer cantidadColeccionesHijas) {
    this.cantidadColeccionesHijas = cantidadColeccionesHijas;
  }
}
