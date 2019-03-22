package com.yanbal.kiosko.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * Clase plantilla para parsear el contenido request del JSON que se recibe en el servicio de
 * registrar sesion movil
 * 
 * @author alex.contreras
 * 
 */
public class JsonRegistrarSesionMovilDataRequest {

  private String usuario;
  private String clave;
  @SerializedName("notificacion_id")
  private String idNotificacion;
  private String pais;
  @SerializedName("dispositivo_id")
  private String idDispositivo;
  @SerializedName("dispositivo_so")
  private String dispositivoSO;

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getIdNotificacion() {
    return idNotificacion;
  }

  public void setIdNotificacion(String idNotificacion) {
    this.idNotificacion = idNotificacion;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getIdDispositivo() {
    return idDispositivo;
  }

  public void setIdDispositivo(String idDispositivo) {
    this.idDispositivo = idDispositivo;
  }

  public String getDispositivoSO() {
    return dispositivoSO;
  }

  public void setDispositivoSO(String dispositivoSO) {
    this.dispositivoSO = dispositivoSO;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

}
