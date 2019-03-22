package com.yanbal.kiosko.dao;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.Rol;

/**
 * Interfaz de capa DAO para la entidad Rol
 * 
 * @author lennin.davila
 * 
 */
public interface RolDao extends KioskoDao<Rol> {

  /**
   * Firma de metodo para obtener un Rol por medio del codigo de Rol
   *
   * @param codigoRol: Cadena con el codigo de Rol Yanbal 
   * @return retorna un objeto Rol
   * @see Rol.
   */    
  Rol obtenerPorCodigo(String codigoRol);

  /**
   * Firma de metodo para obtener un Rol
   *
   * @param correlativo: correlativo del rol
   * @param tabla: tabla del rol
   * @return retorna una Long que indica el correlativo el Rol
   * @see Long.
   */    
  Long obtenerRol(@Param("correlativo") Long correlativo, @Param("tabla") int tabla);
}
