package com.yanbal.kiosko.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.Coleccion;

/**
 * Interfaz de capa DAO para la entidad Coleccion
 * 
 * @author lennin.davila
 * 
 */
public interface ColeccionDao extends KioskoDao<Coleccion> {

  /**
   * Firma de metodo para obtener las colecciones y archivos.
   * 
   * @param pais el codigo del pais por el cual se filtrara en la base de datos.
   * @param codigoRol el codigo del rol por el cual se filtrara en la base de datos.
   * @param fechaSincronizacion la ultima fecha de sincronizacion por el cual se filtrara en la base
   *        de datos.
   * @return retorna un ArrayList con objetos maps que contienen los atributos y valores de las
   *         colecciones y archivos.
   * @see ArrayList.
   */
  List<HashMap<String, Object>> obtenerColeccionArchivo(@Param("pais") String pais,
      @Param("codigoRol") String codigoRol,
      @Param("fechaSincronizacion") Date fechaSincronizacion);

  /**
   * Firma de metodo para obtener la entidad por su correlativo padre.
   * 
   * @param correlativoColeccionPadre el numero de correlativo padre por el cual se filtrara en la
   *        base de datos.
   * @param correlativoPais el numero de correlativo pais por el cual se filtrara en la base de
   *        datos.
   * @return retorna una lista de colecciones.
   * @see retorna el tipo de dato List<Coleccion>.
   */
  List<Coleccion> listarColecciones(
      @Param("correlativoColeccionPadre") Long correlativoColeccionPadre,
      @Param("correlativoPais") Long correlativoPais);

  /**
   * Firma de metodo para obtener la ruta de origen
   *
   * @param correlativoColeccion: correlativo de coleccion
   * @return retorna un List<Coleccion> con objetos Coleccion.
   * @see List<Coleccion>.
   */  
  List<Coleccion> obtenerRutaOrigen(Long correlativoColeccion);

  /**
   * Firma de metodo para eliminar coleccion.
   * 
   * @param correlativoColeccion: correlativo de coleccion
   * @param usuarioModifica: usuario que modifica
   * @return retorna un Long como indicador de exito o error
   * @see Long.
   */  
  Long eliminar(@Param("correlativoColeccion") Long correlativoColeccion,
                @Param("usuarioModifica") String usuarioModifica);

  /**
   * Firma de metodo para obtener por notificar
   *
   * @param correlativoPais: correlativo de coleccion  
   * @return retorna un int como indicador de exito o error.
   * @see int.
   */  
  int obtenerPorNotificar(Long correlativoPais);

}
