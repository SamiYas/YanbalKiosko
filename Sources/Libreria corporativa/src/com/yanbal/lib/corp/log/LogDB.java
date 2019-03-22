


package com.yanbal.lib.corp.log;

/**
* Clase que implementa el Log en base de datos
/**
* @author jcabrera
* @author ovalencia
* @version 1.0 Abril 2015
*/

import com.yanbal.lib.corp.bean.EventoLog;


public class LogDB implements com.yanbal.lib.corp.interfaces.ILog {
	/** Objeto conexion a la base de datos*/
	private Object conexionDB;
	/** Tabla en Base de datos donde se escribe el log*/
	private Object tabla;
	
	/**
	 * Metodo que escribe una linea de Log
	 * @param linea  Objeto que representa una linea de Log 
	 * @return
	 */
	public void escribir(EventoLog linea) {
	
	}
	

}
