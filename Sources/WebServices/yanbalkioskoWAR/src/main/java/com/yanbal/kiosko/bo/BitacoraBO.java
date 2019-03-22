package com.yanbal.kiosko.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.dao.BitacoraDao;
import com.yanbal.kiosko.entities.Bitacora;
import com.yanbal.kiosko.exception.KioskoException;

/**
 * Clase de negocio de la Bitacora, en esta clase se implementa los metodos para la gestion de la
 * bitacora.
 *
 */
@Service
public class BitacoraBO {

  @Autowired
  private BitacoraDao bitacoraDao;

  /**
   * Metodo de negocio que inserta en la base los datos de bitacora de los moviles. Este metodo
   * maneja la transaccion de base de datos.
   * 
   * @param objBitacora un objecto entidad Bitacora que recibe como parametro para insertar en la
   *        base de datos.
   * @return retorna un valor numerico que indica las filas afectadas en la base de datos.
   * @see retorna un tipo de dato Long.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long insertarBitacora(Bitacora objBitacora) throws KioskoException {
    Long resultado;
    try {
      resultado = bitacoraDao.insertar(objBitacora);
    } catch (Exception ex) {
      throw new KioskoException(ex.getMessage(), ex);
    }
    return resultado;
  }
}
