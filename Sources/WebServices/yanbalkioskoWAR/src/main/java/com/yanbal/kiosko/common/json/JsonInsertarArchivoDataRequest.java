package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * insertar archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonInsertarArchivoDataRequest {

  private String token;
  @SerializedName("id_coleccion")
  private Long idColeccion;
  private Long pais;
  private String nombre;
  private String descripcion;
  private Double tamanho;
  private String extension;
  @SerializedName("nro_orden")
  private Integer nroOrden;
  private Integer descargable;
  private Integer destacado;
  @SerializedName("fec_inicio")
  private String fechaInicio;
  @SerializedName("fec_caducidad")
  private String fechaCaducidad;
  @SerializedName("roles")
  private String roles;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getIdColeccion() {
    return idColeccion;
  }

  public void setIdColeccion(Long idColeccion) {
    this.idColeccion = idColeccion;
  }

  public Long getPais() {
    return pais;
  }

  public void setPais(Long pais) {
    this.pais = pais;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Double getTamanho() {
    return tamanho;
  }

  public void setTamanho(Double tamanho) {
    this.tamanho = tamanho;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public Integer getNroOrden() {
    return nroOrden;
  }

  public void setNroOrden(Integer nroOrden) {
    this.nroOrden = nroOrden;
  }

  public Integer getDescargable() {
    return descargable;
  }

  public void setDescargable(Integer descargable) {
    this.descargable = descargable;
  }

  public Integer getDestacado() {
    return destacado;
  }

  public void setDestacado(Integer destacado) {
    this.destacado = destacado;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getFechaCaducidad() {
    return fechaCaducidad;
  }

  public void setFechaCaducidad(String fechaCaducidad) {
    this.fechaCaducidad = fechaCaducidad;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }
}
