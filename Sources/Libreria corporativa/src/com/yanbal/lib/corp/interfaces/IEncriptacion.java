
package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para las tareas de encriptacion
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public interface IEncriptacion {
	
	/** Encripta el mensaje
	 * 
	 * @param mensaje			mensaje a encriptar
	 */
	public void encriptar(Object mensaje);
	
	/** Desencripta el mensaje
	 * 
	 * @param mensaje			mensaje a desencriptar
	 */
	public void desencriptar(Object mensajeEncriptado);
}
