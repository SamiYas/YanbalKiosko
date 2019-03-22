package com.yanbal.kiosko.dao;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.SesionWeb;

/**
 * Interfaz de capa DAO para la entidad SesionWeb
 * 
 * @author lennin.davila
 * 
 */
public interface SesionWebDao extends KioskoDao<SesionWeb> {

  /**
   * Firma de metodo para cerrar la sesion de los usuarios en el administrador web.
   * 
   * @param token es la cadena token que identifica de manera unica a la sesion.
   */
  void eliminarPorToken(@Param("token") String token);

  /**
   * Firma de metodo para obtener por token una sesion web
   *
   * @param token: token de la sesion web 
   * @return retorna un objeto SesionWeb.
   * @see SesionWeb.
   */  
  SesionWeb obtenerPorToken(@Param("token") String token);

  /**
   * Firma de metodo para eliminar las sesiones expiradas
   *
   * @param usuario: nombre del usuario    
   */  
  void eliminarSesionesExpiradas(@Param("nombreUsuario") String nombreUsuario);

  /**
   * Firma de metodo para eliminar el token si ha expirado
   *
   * @param token: token de la sesion web.  
   */  
  void eliminarSiTokenHaExpirado(@Param("token") String token);

}
