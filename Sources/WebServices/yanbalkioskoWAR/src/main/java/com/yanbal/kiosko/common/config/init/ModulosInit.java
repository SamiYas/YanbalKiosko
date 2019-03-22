package com.yanbal.kiosko.common.config.init;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Contiene la lista de modulos a inicializar.
 */
@XStreamAlias("init-modules")
public class ModulosInit {

  @XStreamImplicit
  private List<ModuloInit> modulos;

  public List<ModuloInit> getModulos() {
    return modulos;
  }

  public void setModulos(final List<ModuloInit> modulos) {
    this.modulos = modulos;
  }
}
