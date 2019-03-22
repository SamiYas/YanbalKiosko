package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener archivo
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerArchivoDataResponse implements JsonKioskoDataResponse {

  @SerializedName("id_archivo")
  private Long idArchivo;
  @SerializedName("id_coleccion")
  private Long idColeccion;
  @SerializedName("id_pais")
  private String idPais;
  private String nombre;
  private String descripcion;
  private Double tamanho;
  private String extension;
  @SerializedName("nro_orden")
  private Integer nroOrden;
  private Integer descargable;
  private Integer destacado;
  @SerializedName("ruta_imgprevia")
  private String rutaImagenPrevia;
  @SerializedName("ruta_archivo")
  private String rutaArchivo;
  @SerializedName("fec_inicio")
  private String fechaInicio;
  @SerializedName("fec_caducidad")
  private String fechaCaducidad;
  private String estado;
  private String roles;

  public Long getIdArchivo() {
    return idArchivo;
  }

  public void setIdArchivo(Long idArchivo) {
    this.idArchivo = idArchivo;
  }

  public Long getIdColeccion() {
    return idColeccion;
  }

  public void setIdColeccion(Long idColeccion) {
    this.idColeccion = idColeccion;
  }

  public String getIdPais() {
    return idPais;
  }

  public void setIdPais(String idPais) {
    this.idPais = idPais;
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

  public String getRutaImagenPrevia() {
    return rutaImagenPrevia;
  }

  public void setRutaImagenPrevia(String rutaImagenPrevia) {
    this.rutaImagenPrevia = rutaImagenPrevia;
  }

  public String getRutaArchivo() {
    return rutaArchivo;
  }

  public void setRutaArchivo(String rutaArchivo) {
    this.rutaArchivo = rutaArchivo;
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

}
