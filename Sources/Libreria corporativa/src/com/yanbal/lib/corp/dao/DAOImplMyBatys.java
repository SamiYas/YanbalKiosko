
package com.yanbal.lib.corp.dao;

import com.yanbal.lib.corp.interfaces.IDAO;

/**
 * Clase de la cual deben extender los DAOS de la aplicación
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


//.. interface? abstracción?
public abstract class DAOImplMyBatys implements com.yanbal.lib.corp.interfaces.IDAO {
	
	
	/**
	 * Implementar las llamadas a los SPs de manera generérica mediante MyBatis
	 */
	public void ejecutarSP(Object conexion, Object storedProcedure, Object objEntrada, Object objSalida) {
	
	}
	
	
}
