package com.yanbal.lib.corp.log;

/**
 * Clase que implementa el Log en base de datos /**
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;

import com.yanbal.lib.corp.bean.EventoLog;
import com.yanbal.lib.corp.util.CadenaUtil;


public class LogDB implements com.yanbal.lib.corp.interfaces.ILog {

  private static final Logger LOGGER = LogManager.getLogger(LogDB.class);
  static final String LOG_PROPERTIES_FILE = "properties/log4j.properties";
  /** Objeto conexion a la base de datos */
  private Object conexionDB;
  /** Tabla en Base de datos donde se escribe el log */
  private Object tabla;

  /**
   * Constructor de la clase
   * @throws IOException 
   */
  public LogDB() {
    BasicConfigurator.configure();
    initializeLogger();
  }

  /**
   * Inicializa el Logger leyendo el properties para el Log4J
   * @throws IOException 
   */
  private void initializeLogger(){
    Properties logProperties = new Properties();
    try {
      InputStream propertiesInputStream =
          this.getClass().getClassLoader().getResourceAsStream(LOG_PROPERTIES_FILE);
      logProperties.load(propertiesInputStream);
      PropertyConfigurator.configure(logProperties);
    } catch (IOException e) {      
      LOGGER.error(e.getMessage(), e);
    }
  }


  /**
   * Metodo que escribe una linea de Log
   * 
   * @param linea Objeto que representa una linea de Log
   * @return
   */
	public void escribir(EventoLog linea) {
		if (linea != null) {
			MDC.put("aplicacion", CadenaUtil.getStringValue(linea.getAplicacion()));
			MDC.put("direccionip", CadenaUtil.getStringValue(linea.getDireccionIP()));
			MDC.put("usuario", CadenaUtil.getStringValue(linea.getUsuario()));
			MDC.put("nivelerror", CadenaUtil.getStringValue(linea.getNivelError()));
			MDC.put("clase", CadenaUtil.getStringValue(linea.getClase()));
			MDC.put("refobjprincipal", CadenaUtil.getStringValue(linea.getRefObjPrincipal()));
			MDC.put("mensaje", CadenaUtil.getStringValue(linea.getMensaje()));
			MDC.put("excepcion", CadenaUtil.getStringValue(linea.getExcepcion()));
			MDC.put("pila", CadenaUtil.getStringValue(linea.getPila()));
			LOGGER.error(" ");
		}
	}

}
