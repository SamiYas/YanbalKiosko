package com.yanbal.kiosko.dao;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.Parametro;

/**
 * Interfaz de capa DAO para la entidad Parametro
 * 
 * @author lennin.davila
 * 
 */
public interface ParametroDao extends KioskoDao<Parametro> {
  /**
   * Firma de metodo para obtener la entidad por su nombre.
   * 
   * @param nombre el nombre del Parametro por el cual se filtrara en la base de datos.
   * @return retorna un objecto Parametro.
   * @see Parametro.
   */
  Parametro obtenerPorNombre(@Param("nombre") String nombre);

  /**
   * Firma de metodo para modificar una entidad en la base de datos.
   * 
   * @param parametro es la entidad que se desea modificar en la base de datos.
   * @return retorna un entero que indica la cantidad de registros afectados o modificados en la
   *         base de datos.
   * @see Long es el tipo de dato entero.
   */
  Long modificarPorNombre(Parametro parametro);
}
