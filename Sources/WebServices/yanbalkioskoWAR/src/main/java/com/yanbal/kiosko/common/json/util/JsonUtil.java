package com.yanbal.kiosko.common.json.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * Clase utilitario para el parseo de JSON a Java y viceversa
 * 
 * @author alex.contreras
 * 
 */
public final class JsonUtil {

  private static final Gson GSON = new Gson();

  /**
   * Constructor vacio de la clase JsonUtil
   */  
  private JsonUtil() {

  }

  /**
   * Metodo estatico generico que recibe una cadena en json y devuelve un objeto del tipo especificado
   * 
   * @param json cadena en formato json que se desea parsear
   * @param classType el tipo de objeto que se desea obtener a partir del json recibido
   * @return objeto parseado
   */  
  public static <T> T convertirDeJson(final String json, final Class<T> classType) {
    return GSON.fromJson(json, classType);
  }

  /**
   * Metodo estatico generico que recibe una cadena en json y devuelve un objeto del tipo especificado
   * 
   * @param json cadena en formato json que se desea parsear
   * @param typeOfT tipo de objeto al cual se desea parsear
   * @return objeto parseado
   */  
  public static <T> T convertirDeJson(final String json, final Type typeOfT) {
    return GSON.fromJson(json, typeOfT);
  }

  /**
   * Metodo estatico que convierte un objeto en una cadena json
   * 
   * @param object que se desea convertir en json  
   * @return cadena parseada a partir de un objeto
   */  
  public static String convertirAJson(Object object) {
    return GSON.toJson(object);
  }
}
