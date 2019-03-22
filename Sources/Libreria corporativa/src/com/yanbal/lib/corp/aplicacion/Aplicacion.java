
package com.yanbal.lib.corp.aplicacion;

import com.yanbal.lib.corp.auditoria.AudAccesosDB;
import com.yanbal.lib.corp.bean.Configuracion;
import com.yanbal.lib.corp.interfaces.IAuditoriaAccesos;
import com.yanbal.lib.corp.interfaces.IAuditoriaDatos;
import com.yanbal.lib.corp.interfaces.IConfiguracion;
import com.yanbal.lib.corp.interfaces.IBD;
import com.yanbal.lib.corp.interfaces.IEncriptacion;
import com.yanbal.lib.corp.interfaces.ILog;
import com.yanbal.lib.corp.interfaces.INotificacion;

/**
 * Clase encargada de leer las configuraciones e inicializar los objetos 
 * (técnicos y de negocio que definen el comportamiento de la aplicación)
 * @author jcabrera
 *
 */
public  abstract class Aplicacion {
	
	
	//Los miembros deben ser solo interfaces
	private Aplicacion iAplicacion;
	private IBD iBD;
	private ILog iLogError;
	private IAuditoriaAccesos iAuditoriaAccesos;
	private IAuditoriaDatos iAuditoriaDatos;
	private IEncriptacion iEncriptador;
	private INotificacion iNotificador;
	private IConfiguracion iConfiguracion;
	
	//Objetos de datos
	String nombreAplicacion = null;
	String versionaplicacion = null;
	String rutaConfiguraciones = null; //Esto viene de una variable de WAS
	Configuracion configuracion = null;
	
	
	
	
	private void leerCongiguracion(){
		configuracion = iConfiguracion.leer();
	}
	
	
	/**
	 *Debe instanciar todos los objetos según su configuración e inyectarlos en el contexto de apliación o cargar un factory
	 * Llenar los objetos de datos de la aplicacion
	 */
	
	//@Singleton // debería ser singleton, no se si esto va aquí o en la implementación.. 
	private void Aplicacion() {
		this.leerCongiguracion();
		//Por ejeplo
		this.iAuditoriaAccesos = new AudAccesosDB();
		//Poner en el contexto o en el factory
	
	}
	
	
	
}
