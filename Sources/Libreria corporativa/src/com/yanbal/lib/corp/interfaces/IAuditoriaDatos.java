

package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la escritura de log de auditoria de datos
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


import com.yanbal.lib.corp.bean.EventoAuditoriaDatos;


public interface IAuditoriaDatos {
	
	/** Escribe una linea de log
	 * 
	 * @param linea			bean que representa una linea de log
	 */
	public void escribir(EventoAuditoriaDatos linea);
}
