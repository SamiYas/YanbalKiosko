package com.yanbal.kiosko.common.config.loader;

import java.io.Serializable;
import java.util.Set;

import javax.management.ObjectName;

import com.ibm.websphere.management.AdminService;
import com.ibm.websphere.management.AdminServiceFactory;
import com.yanbal.kiosko.exception.KioskoException;

/**
 * Clase que carga las variables de websphere
 * 
 * @author Alex.Contreras
 * 
 */
@SuppressWarnings("serial")
public class WebsphereVariableLoader implements ConfigLoader, Serializable {

  private static final String ERROR_VARIABLE_KIOSKO = "Error cargando la variable VAR_YANBALKIOSKO";
  private static WebsphereVariableLoader instancia = new WebsphereVariableLoader();

  private String ruta = "";

  /**
   * Constructor vacio de la clase websphere
   */
  public WebsphereVariableLoader() {}

  /**
   * Metodo estatico que obtiene la instacia de carga de configuracion de websphere
   */
  public static WebsphereVariableLoader getInstance() {
    return instancia;
  }

  /**
   * Metodo que inicialia las variables webpshere
   * 
   * @param obj en Object
   */
  public void init(final Object obj) throws KioskoException {
    try {
      if ("".equals(ruta)) {
        AdminService adminService = AdminServiceFactory.getAdminService();
        ObjectName queryName = new ObjectName("WebSphere:*,type=AdminOperations");
        Set objs = adminService.queryNames(queryName, null);
        if (!objs.isEmpty()) {
          ObjectName thisObj = (ObjectName) objs.iterator().next();
          String opName = "expandVariable";
          String signature[] = {"java.lang.String"};
          String params[] = {"${VAR_YANBALKIOSKO}"};
          ruta = (String) adminService.invoke(thisObj, opName, params, signature);
        }
      }
    } catch (Exception e) {
      throw new KioskoException(new StringBuilder(ERROR_VARIABLE_KIOSKO).append(
          WebsphereVariableLoader.class.getName()).toString(), e);
    }
  }

  /**
   * Metodo get que obtiene la ruta
   * 
   * @return retorna una cadena con la ruta.
   */
  public String getRuta() {
    return ruta;
  }

  /**
   * Metodo set que asigna una ruta
   * 
   * @param ruta una cadena que contiene la ruta.
   */
  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

}
