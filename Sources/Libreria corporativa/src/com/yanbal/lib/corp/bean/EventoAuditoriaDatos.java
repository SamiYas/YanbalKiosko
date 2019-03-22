

package com.yanbal.lib.corp.bean;

/**
 * Conjunto de datos que constituyen una linea en log de auditoria de datos
/**
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public class EventoAuditoriaDatos {
	
	/** Aplicacion que realizo la insercion/eliminacion/modificacion del dato */
	private Object aplicacion;
	/** Modulo que realizo la insercion/eliminacion/modificacion del dato */
	private Object modulo;
	/** Usuario que realizo la insercion/eliminacion/modificacion del dato */
	private Object usuario;
	/** Aplicacion que realizo la insercion/eliminacion/modificacion del dato */
	private Object tipoTransaccion;
	/** Table donde se ubica el dato que se inserto/elimino/modifico*/
	private Object tabla;
	/** Dato que se inserto/elimino/modifico*/
	private Object campo;
	/** Fecha en que se  inserto/elimino/modifico el dato */
	private Object fecha;
	/** Hora en que se  inserto/elimino/modifico el dato */
	private Object hora;
	/** Valor anterior del dato */
	private Object valorAnterior;
	/** Nuevo valor del dato */
	private Object valorActual;
	/** Direccion Ip de la maquina donde se realizo la insercion/eliminacion/modificacion del dato */
	private Object direccionIP;

}
