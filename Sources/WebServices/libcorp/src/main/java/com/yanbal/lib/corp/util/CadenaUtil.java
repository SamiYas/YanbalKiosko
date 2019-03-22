package com.yanbal.lib.corp.util;

public class CadenaUtil {

  private CadenaUtil() {    
  }

  public static Object getStringValue(Object valor) {
    return valor != null ? valor : "";
  }

}
