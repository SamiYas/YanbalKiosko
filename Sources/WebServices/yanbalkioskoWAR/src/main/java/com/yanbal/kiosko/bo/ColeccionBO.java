package com.yanbal.kiosko.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.dao.ColeccionDao;
import com.yanbal.kiosko.entities.Coleccion;

@Service
public class ColeccionBO {

  @Autowired
  private ColeccionDao coleccionDao;

  /**
   * Metodo de negocio que devuelve a los moviles los datos de las colecciones y archivos.
   * 
   * 
   * @param String el codigo del pais al cual pertenece el usuario que solita los datos.
   * @param codigoRol el codigo del rol al cual pertenece el usuario que solita los datos.
   * @param fechaSincronizacion la ultima fecha que el usuario movil solicito los datos.
   * @return retorna una lista con objetos maps, el map contiene los atributos y valores que se
   *         devolveran a los moviles.
   * @see retorna un tipo de dato ArrayList.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<HashMap<String, Object>> obtenerColeccionesArchivos(String pais,
      String codigoRol, Date fechaSincronizacion) {
    return coleccionDao.obtenerColeccionArchivo(pais, codigoRol, fechaSincronizacion);
  }

  /**
   * Metodo que obtiene las colecciones filtrando por el correlativo de la coleccion padre
   * 
   * @param correlativoColeccionPadre correlativo de padre
   * @return retorna lista de colecciones hijas
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Coleccion> listarColecciones(Long correlativoColeccionPadre, Long correlativoPais) {
    return coleccionDao.listarColecciones(correlativoColeccionPadre, correlativoPais);
  }

  /**
   * Metodo que inserta una nueva coleccion en la base de datos
   * 
   * @param coleccion la coleccion a insertar
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public synchronized Long insertarColeccion(Coleccion coleccion) {
    return coleccionDao.insertar(coleccion);
  }

  /**
   * Metodo que elimina una coleccion
   * 
   * @param idColeccion id de la coleccion a eliminar
   * @param usuarioModifica usuario que elimina
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long eliminarColeccion(Long correlativoColeccion, String usuarioModifica) {
    return coleccionDao.eliminar(correlativoColeccion, usuarioModifica);
  }

  /**
   * Metodo que obtiene una coleccion a partir de su numero correlativo
   * 
   * @param correlativoColeccion correlativo de la coleccion
   * @return la coleccion obtenido
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public Coleccion obtenerColeccion(Long correlativoColeccion) {
    return coleccionDao.obtenerPorCorrelativo(correlativoColeccion);
  }

  /**
   * Metodo que actualiza una coleccion
   * 
   * @param coleccion coleccion con datos a actualizar
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public synchronized Long actualizarColeccion(Coleccion coleccion) {
    return coleccionDao.modificar(coleccion);
  }

  /**
   * Metodo que obtiene la ruta de la coleccion
   * 
   * @param correlativoColeccion correlativo de la coleccion
   * @return ruta encontrada
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Coleccion> obtenerRutaOrigen(Long correlativoColeccion) {
    return coleccionDao.obtenerRutaOrigen(correlativoColeccion);
  }

  /**
   * Metodo que obtiene la cantidad de archivos por notificar
   * 
   * @param correlativoPais correlativo del pais
   * @return cantidad por notificar
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public int obtenerPorNotificar(Long correlativoPais) {
    return coleccionDao.obtenerPorNotificar(correlativoPais);
  }

}
