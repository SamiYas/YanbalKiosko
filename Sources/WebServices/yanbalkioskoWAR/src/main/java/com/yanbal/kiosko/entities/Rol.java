package com.yanbal.kiosko.entities;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase pantilla de la tabla KSK_ROL
 * 
 * @author lennin.davila
 * 
 */
public class Rol extends KioskoEntidad {

  private static final long serialVersionUID = 5978378551773863318L;

  private Long correlativoRol;
  private String nombre;
  private String codigoRol;

  public Rol() {    
  }

  /**
   * Obtiene el numero correlativo del pais
   * 
   * @return numero correlativo del pais
   */
  public Long getCorrelativoRol() {
    return correlativoRol;
  }

  /**
   * Establece un numero correlativo al pais
   * 
   * @param correlativoPais numero correlativo del pais
   */
  public void setCorrelativoRol(Long correlativoRol) {
    this.correlativoRol = correlativoRol;
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
  public String getCodigoRol() {
    return codigoRol;
  }

  /**
   * Establece el codigo del pais
   * 
   * @param codigoPais codigo del pais
   */
  public void setCodigoRol(String codigoRol) {
    this.codigoRol = codigoRol;
  }
}
