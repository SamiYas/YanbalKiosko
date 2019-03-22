package com.yanbal.kiosko.common.config.init;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Realiza parseo para inicializar archivos xmls usando XStream
 */
@XStreamAlias("init-module")
public class ModuloInit {

  @XStreamAlias("parseable-class")
  private String claseParseable;
  @XStreamAlias("init-class")
  private String claseInit;
  @XStreamAlias("config-file")
  private String archivoConfig;

  public String getClaseInit() {
    return claseInit;
  }

  public void setClaseInit(final String claseInit) {
    this.claseInit = claseInit;
  }

  public String getArchivoConfig() {
    return archivoConfig;
  }

  public void setArchivoConfig(final String archivoConfig) {
    this.archivoConfig = archivoConfig;
  }

  public void setParseableClass(final String parseableClass) {
    this.claseParseable = parseableClass;
  }

  public String getParseableClass() {
    return claseParseable;
  }
}
