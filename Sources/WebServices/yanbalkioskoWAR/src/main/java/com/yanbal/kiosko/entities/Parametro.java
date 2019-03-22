package com.yanbal.kiosko.entities;

import com.yanbal.kiosko.common.core.KioskoEntidad;

/**
 * Clase pantilla de la tabla KSK_PARAMETRO
 * 
 * @author lennin.davila
 * 
 */
public class Parametro extends KioskoEntidad {
  private Long correlativoParametro;
  private String nombre;
  private String valor;

  /**
   * @return the correlativoParametro
   */
  public Long getCorrelativoParametro() {
    return correlativoParametro;
  }

  /**
   * @param correlativoParametro the correlativoParametro to set
   */
  public void setCorrelativoParametro(Long correlativoParametro) {
    this.correlativoParametro = correlativoParametro;
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
   * @return the valor
   */
  public String getValor() {
    return valor;
  }

  /**
   * @param valor the valor to set
   */
  public void setValor(String valor) {
    this.valor = valor;
  }
}
