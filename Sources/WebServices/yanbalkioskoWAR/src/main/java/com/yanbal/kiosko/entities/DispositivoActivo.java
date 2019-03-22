package com.yanbal.kiosko.entities;

import java.util.Date;
/**
 * Clase plantilla de la tabla KSK_DISPOSITIVO_ACTIVO
 * 
 * @author alex.contreras
 *
 */
public class DispositivoActivo extends Usuario {

  private static final long serialVersionUID = 1L;

  private Long correlativoDispositivoActivo;
  private Long correlativoPais;
  private String codigoPais;
  private String clave;
  private String notificacionIdentificador;
  private String dispositivoSis;
  private String dispositivoIdentificador;
  private String token;
  private Date fechaRegistro;
  private Date fechaUltimaSinc;

  public DispositivoActivo() {    
  }

  public DispositivoActivo(Long correlativoPais, String usuario, String clave,
      String notificacionIdentificador, String dispositivoSis, String dispositivoIdentificador) {
    super();
    this.correlativoPais = correlativoPais;
    this.clave = clave;
    this.notificacionIdentificador = notificacionIdentificador;
    this.dispositivoSis = dispositivoSis;
    this.dispositivoIdentificador = dispositivoIdentificador;
    super.setNombreUsuario(usuario);
  }

  public DispositivoActivo(String codigoPais, String usuario, String clave,
      String notificacionIdentificador, String dispositivoSis, String dispositivoIdentificador) {
    super();
    this.codigoPais = codigoPais;
    this.clave = clave;
    this.notificacionIdentificador = notificacionIdentificador;
    this.dispositivoSis = dispositivoSis;
    this.dispositivoIdentificador = dispositivoIdentificador;
    super.setNombreUsuario(usuario);
  }

  public Long getCorrelativoDispositivoActivo() {
    return correlativoDispositivoActivo;
  }

  public void setCorrelativoDispositivoActivo(Long correlativoDispositivoActivo) {
    this.correlativoDispositivoActivo = correlativoDispositivoActivo;
  }

  public Long getCorrelativoPais() {
    return correlativoPais;
  }

  public void setCorrelativoPais(Long correlativoPais) {
    this.correlativoPais = correlativoPais;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getNotificacionIdentificador() {
    return notificacionIdentificador;
  }

  public void setNotificacionIdentificador(String notificacionIdentificador) {
    this.notificacionIdentificador = notificacionIdentificador;
  }

  public String getDispositivoSis() {
    return dispositivoSis;
  }

  public void setDispositivoSis(String dispositivoSis) {
    this.dispositivoSis = dispositivoSis;
  }

  public String getDispositivoIdentificador() {
    return dispositivoIdentificador;
  }

  public void setDispositivoIdentificador(String dispositivoIdentificador) {
    this.dispositivoIdentificador = dispositivoIdentificador;
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

  public Date getFechaUltimaSinc() {
    return fechaUltimaSinc;
  }

  public void setFechaUltimaSinc(Date fechaUltimaSinc) {
    this.fechaUltimaSinc = fechaUltimaSinc;
  }

  public String getCodigoPais() {
    return codigoPais;
  }

  public void setCodigoPais(String codigoPais) {
    this.codigoPais = codigoPais;
  }

}
