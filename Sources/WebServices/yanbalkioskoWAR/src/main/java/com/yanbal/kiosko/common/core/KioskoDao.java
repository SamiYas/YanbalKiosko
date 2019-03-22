package com.yanbal.kiosko.common.core;

import java.util.List;

/**
 * Interface base de acceso a datos, en esta interface se encuentra las firmas de los metodos de
 * acceso a datos basicos de toda entidad.
 * 
 * Esta interfaz es generica y acepta cualquier objeto que sea hijo de la clase abstracta
 * KioskoEntidad.
 */
public interface KioskoDao<BE extends KioskoEntidad> {
  /**
   * Firma de metodo para obtener la entidad por un correlativo.
   * 
   * @param correlativo el numero de correlativo por el cual se filtrara en la base de datos.
   * @return retorna un objecto BE, BE representa a cualquier clase hija de KioskoEntidad.
   * @see BE extends KioskoEntidad.
   */
  BE obtenerPorCorrelativo(Long correlativo);

  /**
   * Firma de metodo para obtener todas las datos de un tipo de entidad.
   * 
   * @return retorna una lista de objecto BE, BE representa a cualquier clase hija de KioskoEntidad.
   * @see List<BE> es la lista de objetos.
   */
  List<BE> listarTodo();

  /**
   * Firma de metodo para obtener todas las datos de un tipo de entidad.
   * 
   * @param correlativo es el correlativo de la entidad que se desea eliminar.
   * @return retorna una lista de objecto BE, BE representa a cualquier clase hija de KioskoEntidad.
   * @see List<BE> es la lista de objetos.
   */
  List<BE> listarTodoPorCorrelativo(Long correlativo);

  /**
   * Firma de metodo para insertar una entidad en la base de datos.
   * 
   * @param entidad es la entidad que se desea insertar en la base de datos.
   * @return retorna un entero que indica la cantidad de registros afectados o insertados en la base
   *         de datos.
   * @see Long es el tipo de dato entero.
   */
  Long insertar(BE entidad);

  /**
   * Firma de metodo para modificar una entidad en la base de datos.
   * 
   * @param entidad es la entidad que se desea modificar en la base de datos.
   * @return retorna un entero que indica la cantidad de registros afectados o modificados en la
   *         base de datos.
   * @see Long es el tipo de dato entero.
   */
  Long modificar(BE entidad);

  /**
   * Firma de metodo para eliminar una entidad en la base de datos.
   * 
   * @param correlativo es el correlativo de la entidad que se desea eliminar.
   */
  void eliminarPorCorrelativo(Long correlativo);
}
