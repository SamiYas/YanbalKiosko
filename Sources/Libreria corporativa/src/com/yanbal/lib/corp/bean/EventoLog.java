
package com.yanbal.lib.corp.bean;
/**
 * Conjunto de datos que constituyen una linea en log de errores
/**
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


public class EventoLog {

	/** Aplicacion que genero el error*/
	private Object aplicacion;
	/** Direccion IP de la maquina desde donde se genero el error*/
	private Object direccionIP;
	/** Usuario logueado en la aplicacion cuando ocurrio el error*/
	private Object usuario;
	/**  Severidad del error, valores posibles enum NivelError (ERR: Errro,  ADV: Advertencia)
	private Object nivelError;
	/** Clase donde se origino el error*/
	private Object clase;
	/** Objeto principal que instancio la clase (si aplica)*/
	private Object refObjPrincipal;
	/** Datos adicionales del error*/
	private Object mensaje;
	/** Mensaje de excepcion*/
	private Object excepcion;
	/** Pila de llamadas (stacktrace)*/
	private Object pila;
}
