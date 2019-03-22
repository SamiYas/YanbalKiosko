package com.yanbal.kiosko.dao;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.Pais;

/**
 * Interfaz de capa DAO para la entidad Pais
 * 
 * @author lennin.davila
 * 
 */
public interface PaisDao extends KioskoDao<Pais> {
  
  /**
   * Firma de metodo para inhabilitar los paises
   */  
  void inhabilitarPaises();
  
  /**
   * Firma de metodo que genera los formato de archivo para los paises nuevos
   */  
  void generarFormatoArchivoPaises();  
  
  /**
   * Firma de metodo para modificar un Pais en la base de datos.
   * 
   * @param pais es la entidad que se desea modificar en la base de datos.
   * @return retorna un entero que indica la cantidad de registros afectados o modificados en la
   *         base de datos.
   * @see Long es el tipo de dato entero.
   */
  Long insertarOModificarPorCodigoPais(Pais pais);
  
}
