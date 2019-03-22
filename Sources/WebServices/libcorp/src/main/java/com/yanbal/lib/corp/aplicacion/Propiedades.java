package com.yanbal.lib.corp.aplicacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase encargada de leer las propiedades de un aplicativo
 * 
 * @author lennin.davila
 *
 */
public class Propiedades {
  Properties prop = null;

  /**
   * Contructor de la clase
   */
  public Propiedades(String archivo) throws IOException {
    InputStream propertiesInputStream =
        this.getClass().getClassLoader().getResourceAsStream(archivo);
    prop = new Properties();
    prop.load(propertiesInputStream);
  }

  /**
   * Contructor de la clase
   */
  public Propiedades(InputStream archivo) throws IOException {
    prop = new Properties();
    prop.load(archivo);
  }

  /**
   * Metodo que retorna el valor de la propiedad segun el nombre de atributo.
   */
  public String getValor(String propiedad) {
    return prop.getProperty(propiedad);
  }
}
