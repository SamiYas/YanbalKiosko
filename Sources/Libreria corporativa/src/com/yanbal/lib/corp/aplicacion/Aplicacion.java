
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
 * (t�cnicos y de negocio que definen el comportamiento de la aplicaci�n)
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
	 *Debe instanciar todos los objetos seg�n su configuraci�n e inyectarlos en el contexto de apliaci�n o cargar un factory
	 * Llenar los objetos de datos de la aplicacion
	 */
	
	//@Singleton // deber�a ser singleton, no se si esto va aqu� o en la implementaci�n.. 
	private void Aplicacion() {
		this.leerCongiguracion();
		//Por ejeplo
		this.iAuditoriaAccesos = new AudAccesosDB();
		//Poner en el contexto o en el factory
	
	}
	
	
	
}
