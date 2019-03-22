package com.yanbal.kiosko.interfaces.rest.servicios.json;

/**
 * Clase plantilla para el parseo del contenido de los datos del usuario del response del servicio de yanbal
 * 
 * @author alex.contreras
 * 
 */
public class DatosUsuarioYanbal {

  private String FlagValidacion;
  private String TipoUsuario;
  private String Usuario;
  private String Status;
  private String Perfil;

  public String getFlagValidacion() {
    return FlagValidacion;
  }

  public void setFlagValidacion(String flagValidacion) {
    FlagValidacion = flagValidacion;
  }

  public String getTipoUsuario() {
    return TipoUsuario;
  }

  public void setTipoUsuario(String tipoUsuario) {
    TipoUsuario = tipoUsuario;
  }

  public String getUsuario() {
    return Usuario;
  }

  public void setUsuario(String usuario) {
    Usuario = usuario;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public String getPerfil() {
    return Perfil;
  }

  public void setPerfil(String perfil) {
    Perfil = perfil;
  }

}
