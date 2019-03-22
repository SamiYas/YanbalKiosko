package com.yanbal.kiosko.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.DispositivoActivo;

/**
 * Interfaz de capa DAO para la entidad DispositivoActivo
 * 
 * @author lennin.davila
 * 
 */
public interface DispositivoActivoDao extends KioskoDao<DispositivoActivo> {
  /**
   * Firma de metodo para cerrar la sesion de los usuarios en los dispositivos moviles
   * 
   * @param token es la cadena token que identifica de manera unica a la sesion.
   */
  Long registrarSesion(DispositivoActivo entidad);

  /**
   * Firma de metodo eliminar por token
   *
   * @param token: token identificador de sesion 
   */  
  void eliminarPorToken(@Param("token") String token);

  /**
   * Firma de metodo para obtener un dispositivo por medio del token
   *
   * @param token: token identificador de sesion 
   * @return retorna un objeto DispositivoActivo
   * @see DispositivoActivo.
   */  
  DispositivoActivo obtenerPorToken(@Param("token") String token);

  /**
   * Firma de metodo para obtener por notificar
   *
   * @param correlativoPais: correlativo de pais
   * @param diasInactividad: cantidad de dias de inactividad de la sesion  
   * @return retorna una lista List<DispositivoActivo> con los dispositivos
   * @see List<DispositivoActivo>.
   */  
  List<DispositivoActivo> obtenerDispositivos(@Param("correlativoPais") Long correlativoPais,
      @Param("diasInactividad") int diasInactividad);
}
