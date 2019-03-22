package com.yanbal.kiosko.common.config.loader;

import com.yanbal.kiosko.exception.KioskoException;

/**
 * Interface de configuracion de la aplicativo
 * 
 * @author Alex.Contreras
 * 
 */
public interface ConfigLoader {
  /**
   * Firma del metodo init
   * @param obj en Object
   */  
  void init(Object obj) throws KioskoException;

}
