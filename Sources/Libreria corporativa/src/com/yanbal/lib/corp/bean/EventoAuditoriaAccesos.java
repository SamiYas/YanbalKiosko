
package com.yanbal.lib.corp.bean;

/**
 * Conjunto de datos que constituyen una linea en log de auditoria de accesos
/**
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public class EventoAuditoriaAccesos  {
	
	/** Nombre del usuairo que accedio a la aplicacion */
	private Object usuario;
	
	/** Tipo de sesion que Valores posibles enum TipoSesion (INICIO: Inicio de Sesion, FIN: Fin de Sesion */
	private Object tipoSesion;
	/** Tipo de acceso que Valores posibles  enum TipoAcceso (VALIDO, FALLIDO */
	private Object tipoAcceso;
	/** Fecha y hora del acceso*/
	private Object fechaHora;
	/** Direccion IP de la maquina desde donde se realiza el acceso */
	private Object direccionIP;
	/** Nombre de la maquina desde donde se realiza el acceso */
	private Object nombreEquipo;

}
