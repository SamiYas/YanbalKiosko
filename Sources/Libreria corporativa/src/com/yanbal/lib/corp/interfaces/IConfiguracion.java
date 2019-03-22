

package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la lectura de configuracion de aplicacion
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

import com.yanbal.lib.corp.bean.Configuracion;



public abstract class IConfiguracion {
	/** Ruta del archivo de configuracion*/
	private Object rutaArchivo;
	/** Nombre del archivo de configuracion*/
	private Object nombreArchivo;
	
	
	/** Metodo para la lectura del archivo*/
	public Configuracion leer() {
		return null;
	}
}
