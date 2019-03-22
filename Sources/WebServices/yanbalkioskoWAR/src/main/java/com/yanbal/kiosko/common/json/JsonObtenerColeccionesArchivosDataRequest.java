package com.yanbal.kiosko.common.json;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * obtener colecciones archivos
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerColeccionesArchivosDataRequest {

  private String token;
  private String pais;
  private String rol;
  private String fecha_sincronizacion;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }


  public String getFecha_sincronizacion() {
    return fecha_sincronizacion;
  }

  public void setFecha_sincronizacion(String fecha_sincronizacion) {
    this.fecha_sincronizacion = fecha_sincronizacion;
  }
}
