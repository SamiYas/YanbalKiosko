package com.yanbal.kiosko.entities;

import com.yanbal.kiosko.common.core.KioskoEntidad;
/**
 * Clase padre de la sesion de dispositivo y web
 * 
 * @author lennin.davila
 * 
 */
public class Usuario extends KioskoEntidad {

  private String nombreUsuario;

  public Usuario() {
  }

  public Usuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }
}
