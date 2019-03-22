package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener datos usuario
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerDatosUsuarioRequest {

  private String pais;
  private String tipoUsuario;
  private String usuario;
  private String password;

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(String tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
