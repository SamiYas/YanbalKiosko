package com.yanbal.lib.corp.entities;

import com.yanbal.lib.corp.enums.Plataforma;

/**
 * Clase bean que con los atributos para recibir los tokens de dispositivo.
 * 
 * @author lennin.davila
 *
 */
public class TokenPush {
  private String tokenDevice;
  private Plataforma plataforma;

  public TokenPush(String tokenDevice, Plataforma plataforma) {
    super();
    this.tokenDevice = tokenDevice;
    this.plataforma = plataforma;
  }

  /**
   * @return the token_device
   */
  public String getTokenDevice() {
    return tokenDevice;
  }

  /**
   * @param token_device the token_device to set
   */
  public void setTokenDevice(String tokenDevice) {
    this.tokenDevice = tokenDevice;
  }

  /**
   * @return the plataforma
   */
  public Plataforma getPlataforma() {
    return plataforma;
  }

  /**
   * @param plataforma the plataforma to set
   */
  public void setPlataforma(Plataforma plataforma) {
    this.plataforma = plataforma;
  }
}
