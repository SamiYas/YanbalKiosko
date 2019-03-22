

package com.yanbal.lib.corp.log;

/**
 * Clase tipo Factory para la creacion de Logs
/**
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */



import com.yanbal.lib.corp.bean.EventoLog;
import com.yanbal.lib.corp.interfaces.ILog;


public class LogBO implements ILog {
	
	/**
	 * Metodo que devuelve un log en base al parametro tipo
	 * @param tipoLog  tipo de Log, valores posibles Enum TipoLog (BD: Base de Datos, WS: Web Service, FL: File) 
	 * @return
	 */
	public ILog getLog(String tipoLog) {
	  return null;
	}

	@Override
	public void escribir(EventoLog linea) {
		// TODO Auto-generated method stub
		
	}
}
