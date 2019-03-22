
package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la gestion de conexiones a base de datos
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public interface IBD {
	
	/** Metodo para abrir la conexion */
	public void conectar();
	/** Metodo para abrir la conexion */
	public void desconectar();
	/** Metodo para abrir la conexion */
	public void iniciaTrx();
	/** Metodo para comprometer una transaccion */
	public void commit();
	/** Metodo para indicar que la conexion realiza o no autocommit
	 *  * @param autocommit			valor tipo boolean que indica si la conexion realiza autocommit
	 *   **/
	public void autocommit(boolean autocommit);
}
