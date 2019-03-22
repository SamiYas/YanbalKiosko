package com.yanbal.kiosko.interfaces.rest.servicios.json;
/**
 * Clase plantilla para el parseo de paises del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class PaisYanbal {
  private String CodigoPais;
  private String NombrePais;
  private String NombreCortoPais;
  private Object TipoPais;
  private Object EstadoPais;
  private Object NombreMoneda;
  private Object NombreCortoMoneda;
  private Object SimboloMoneda;
  private Object Usuario;
  private Object FechaActualizacion;

  /**
   * @return the codigoPais
   */
  public String getCodigoPais() {
    return CodigoPais;
  }

  /**
   * @param codigoPais the codigoPais to set
   */
  public void setCodigoPais(String codigoPais) {
    CodigoPais = codigoPais;
  }

  /**
   * @return the nombrePais
   */
  public String getNombrePais() {
    return NombrePais;
  }

  /**
   * @param nombrePais the nombrePais to set
   */
  public void setNombrePais(String nombrePais) {
    NombrePais = nombrePais;
  }

  /**
   * @return the nombreCortoPais
   */
  public String getNombreCortoPais() {
    return NombreCortoPais;
  }

  /**
   * @param nombreCortoPais the nombreCortoPais to set
   */
  public void setNombreCortoPais(String nombreCortoPais) {
    NombreCortoPais = nombreCortoPais;
  }

  /**
   * @return the tipoPais
   */
  public Object getTipoPais() {
    return TipoPais;
  }

  /**
   * @param tipoPais the tipoPais to set
   */
  public void setTipoPais(Object tipoPais) {
    TipoPais = tipoPais;
  }

  /**
   * @return the estadoPais
   */
  public Object getEstadoPais() {
    return EstadoPais;
  }

  /**
   * @param estadoPais the estadoPais to set
   */
  public void setEstadoPais(Object estadoPais) {
    EstadoPais = estadoPais;
  }

  /**
   * @return the nombreMoneda
   */
  public Object getNombreMoneda() {
    return NombreMoneda;
  }

  /**
   * @param nombreMoneda the nombreMoneda to set
   */
  public void setNombreMoneda(Object nombreMoneda) {
    NombreMoneda = nombreMoneda;
  }

  /**
   * @return the nombreCortoMoneda
   */
  public Object getNombreCortoMoneda() {
    return NombreCortoMoneda;
  }

  /**
   * @param nombreCortoMoneda the nombreCortoMoneda to set
   */
  public void setNombreCortoMoneda(Object nombreCortoMoneda) {
    NombreCortoMoneda = nombreCortoMoneda;
  }

  /**
   * @return the simboloMoneda
   */
  public Object getSimboloMoneda() {
    return SimboloMoneda;
  }

  /**
   * @param simboloMoneda the simboloMoneda to set
   */
  public void setSimboloMoneda(Object simboloMoneda) {
    SimboloMoneda = simboloMoneda;
  }

  /**
   * @return the usuario
   */
  public Object getUsuario() {
    return Usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(Object usuario) {
    Usuario = usuario;
  }

  /**
   * @return the fechaActualizacion
   */
  public Object getFechaActualizacion() {
    return FechaActualizacion;
  }

  /**
   * @param fechaActualizacion the fechaActualizacion to set
   */
  public void setFechaActualizacion(Object fechaActualizacion) {
    FechaActualizacion = fechaActualizacion;
  }
}
