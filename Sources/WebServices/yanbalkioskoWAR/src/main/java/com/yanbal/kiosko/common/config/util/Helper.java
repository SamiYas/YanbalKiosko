package com.yanbal.kiosko.common.config.util;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Clase que carga las variables de websphere
 * 
 * @author Alex.Contreras
 * 
 */
public class Helper {
  
  private static final String INSTANCIA_METODO = "getInstance";
  
  /**
   * Constructor vacio de clase
   */  
  private Helper(){
    
  }

  /**
   * Metodo estatico que obtiene una instancia de una clase
   * @param modulo: nombre de la clase
   * @param esSingleton: valor booleano que especifica si el parametro enviado es unico.
   * @return retorna un Object
   * @throws ClassNotFoundException 
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws NoSuchMethodException 
   * @throws SecurityException 
   * @throws InvocationTargetException 
   * @throws IllegalArgumentException 
   */  
  public static Object getInstance(String modulo, boolean esSingleton) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
    if (esSingleton) {
      Method method = Class.forName(modulo).getMethod(INSTANCIA_METODO, new Class[0]);
      return method.invoke(null, new Object[0]);
    }
    return Class.forName(modulo).newInstance();
  }

  /**
   * Metodo estatico que obtiene una clase
   * @param modulo: nombre de la clase
   * @return retorna un tipo Class.
   * @throws ClassNotFoundException 
   */ 
  public static Class<?> getClass(String modulo) throws ClassNotFoundException {
    return Class.forName(modulo);
  }

  /**
   * Metodo estatico que obtiene un ImputSream a partir de una cadena
   * @param name: nombre del recurso del cual se obtendra el imputsream
   * @return retorna el imputStream a partir de la cadena enviada
   */  
  public static InputStream toInputStream(String name) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    return loader.getResourceAsStream(name);
  }

}
