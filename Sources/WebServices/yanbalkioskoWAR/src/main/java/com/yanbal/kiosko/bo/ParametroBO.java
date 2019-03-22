package com.yanbal.kiosko.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.dao.ParametroDao;
import com.yanbal.kiosko.entities.Parametro;

/**
 * Clase de negocio de Parametro, en esta clase se implementa los metodos para la gestion de los
 * Parametros.
 * 
 */
@Service
public class ParametroBO {
  @Autowired
  private ParametroDao parametroDao;

  /**
   * Metodo de negocio que obtiene la ultima fecha de actualizacion.
   * 
   * @return retorna un objecto Parametro, contiene la ultima de fecha de actualizacion.
   * @see Parametro objeto entidad Parametro.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public Parametro obtenerFechaUltimaActualizacion(String nombre) {
    return parametroDao.obtenerPorNombre(nombre);
  }

  /**
   * Metodo de negocio que actualiza la ultima fecha de actualizacion.
   * 
   * @return retorna un valor numerico, contiene la cantidad de registros que se modificaron en la
   *         base de datos.
   * @see Long.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long actualizarFechaUltimaActualizacion(Parametro objParametro) {
    return parametroDao.modificarPorNombre(objParametro);
  }
}
