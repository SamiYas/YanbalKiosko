
package com.yanbal.lib.corp.bd;


/**
 * Implementación de Gestion de BD para DB2
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public class DBMySQL implements com.yanbal.lib.corp.interfaces.IBD {
	
	/** Nombre del datasource en el servidor WAS*/
	private Object nombreJDNI;
	
	/** Objeto de conexion*/
	private Object Connection;
	
	
	/** Metodo para abrir la conexion */
	public void conectar() {
	
	}
	
	/** Metodo para cerrar la conexion */
	public void desconectar() {
	
	}
	
	/** Metodo para iniciar una transaccion */
	public void iniciaTrx() {
	
	}
	
	
	/** Metodo para comprometer una transaccion */
	public void commit() {
	
	}
	
	/** Metodo para indicar que la conexion realiza o no autocommit
	 *  * @param autocommit			valor tipo boolean que indica si la conexion realiza autocommit
	 *   **/
	public void autocommit(boolean autocommit) {
	
	}

}
