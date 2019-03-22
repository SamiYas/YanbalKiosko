package com.yanbal.kiosko.entities;

import java.util.Date;

/**
 * Clase pantilla de la tabla KSK_SESION_WEB
 * 
 * @author lennin.davila
 * 
 */
public class SesionWeb extends Usuario {
  private Long correlativoSesionWeb;
  private Long correlativoPais;
  private String pais;
  private String clave;
  private String token;
  private Date fechaRegistro;
  private Long correlativoRol;

  public SesionWeb() {    
  }

  public SesionWeb(Long correlativoPais, String usuario, String clave) {
    super();
    this.correlativoPais = correlativoPais;
    super.setNombreUsuario(usuario);
    this.clave = clave;
  }

  public Long getCorrelativoSesionWeb() {
    return correlativoSesionWeb;
  }

  public void setCorrelativoSesionWeb(Long correlativoSesionWeb) {
    this.correlativoSesionWeb = correlativoSesionWeb;
  }

  public Long getCorrelativoPais() {
    return correlativoPais;
  }

  public void setCorrelativoPais(Long correlativoPais) {
    this.correlativoPais = correlativoPais;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public Long getCorrelativoRol() {
    return correlativoRol;
  }

  public void setCorrelativoRol(Long correlativoRol) {
    this.correlativoRol = correlativoRol;
  }
}
