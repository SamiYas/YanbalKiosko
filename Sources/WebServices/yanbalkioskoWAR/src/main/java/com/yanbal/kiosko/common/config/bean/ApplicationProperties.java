package com.yanbal.kiosko.common.config.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 *  Clase de model que define las propiedades del sistema
 * 
 * @author Alex.Contreras
 * 
 */
@XStreamAlias("ApplicationProperties")
public class ApplicationProperties implements Serializable{
  private static final long serialVersionUID = 5110777510892034281L;
  @XStreamAlias("directorioCatalogosId")
  private String directorioCatalogosId;
  private String directorioCatalogos;
  @XStreamAlias("directorioMiniaturaId")
  private String directorioMiniaturaId;
  private String directorioMiniatura;

  public String getDirectorioCatalogosId() {
    return directorioCatalogosId;
  }

  public void setDirectorioCatalogosId(String directorioCatalogosId) {
    this.directorioCatalogosId = directorioCatalogosId;
  }

  public String getDirectorioCatalogos() {
    return directorioCatalogos;
  }

  public void setDirectorioCatalogos(String directorioCatalogos) {
    this.directorioCatalogos = directorioCatalogos;
  }

  public String getDirectorioMiniaturaId() {
    return directorioMiniaturaId;
  }

  public void setDirectorioMiniaturaId(String directorioMiniaturaId) {
    this.directorioMiniaturaId = directorioMiniaturaId;
  }

  public String getDirectorioMiniatura() {
    return directorioMiniatura;
  }

  public void setDirectorioMiniatura(String directorioMiniatura) {
    this.directorioMiniatura = directorioMiniatura;
  }
}
