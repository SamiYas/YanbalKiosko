package com.yanbal.kiosko.entities;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase pantilla de la tabla KSK_PAIS
 * 
 * @author lennin.davila
 * 
 */
public class Pais extends KioskoEntidad {

  private static final long serialVersionUID = 5978378551773863318L;

  private Long correlativoPais;
  private String nombre;
  private String codigoPais;

  public Pais() {    
  }

  public Pais(String nombre, String codigoPais) {
    this.nombre = nombre;
    this.codigoPais = codigoPais;
  }


  /**
   * Obtiene el numero correlativo del pais
   * 
   * @return numero correlativo del pais
   */
  public Long getCorrelativoPais() {
    return correlativoPais;
  }

  /**
   * Establece un numero correlativo al pais
   * 
   * @param correlativoPais numero correlativo del pais
   */
  public void setCorrelativoPais(Long correlativoPais) {
    this.correlativoPais = correlativoPais;
  }

  /**
   * Obtiene el nombre del pais
   * 
   * @return nombre del pais
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del pais
   * 
   * @param nombre nombre del pais
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el codigo del pais
   * 
   * @return codigo del pais
   */
  public String getCodigoPais() {
    return codigoPais;
  }

  /**
   * Establece el codigo del pais
   * 
   * @param codigoPais codigo del pais
   */
  public void setCodigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
  }
}
