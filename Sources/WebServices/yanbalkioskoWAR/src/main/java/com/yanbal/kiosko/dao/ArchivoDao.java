package com.yanbal.kiosko.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.Archivo;

/**
 * Interfaz de capa DAO para la entidad Archivo
 * 
 * @author lennin.davila
 * 
 */
public interface ArchivoDao extends KioskoDao<Archivo> {
  /**
   * Firma de metodo para obtener la lista de archivos.
   *
   * @param correlativoColeccion: correlativo de coleccion
   * @param nombre: nombre del archivo
   * @param descripcion: descripcion del archivo
   * @param correlativoPais: correlativo del pais
   * @return retorna un List<Archivo> con objetos Archivo.
   * @see List<Archivo>.
   */
  List<Archivo> listarArchivos(@Param("correlativoColeccion") Long correlativoColeccion,
                              @Param("nombre") String nombre, 
                              @Param("descripcion") String descripcion,
                              @Param("correlativoPais") Long correlativoPais);
  
  /**
   * Firma de metodo para mover archivo
   *
   * @param correlativoArchivo: correlativo de archivo
   * @param correlativoColeccion: correlativo de coleccion
   * @param usuarioModifica: usuario que modifica el archivo
   * @return retorna un Long de resultado de exito o error.
   * @see Long.
   */
  Long moverArchivo(@Param("correlativoArchivo") Long correlativoArchivo,
                    @Param("correlativoColeccion") Long correlativoColeccion,
                    @Param("usuarioModifica") String usuarioModifica);
  
  /**
   * Firma de metodo para eliminar archivo
   *
   * @param correlativoArchivo: correlativo de archivo
   * @param usuarioModifica:  usuario que modifica el archivo
   * @return retorna un Long de resultado de exito o error.
   * @see Long.
   */
  void eliminar(@Param("correlativoArchivo") Long correlativoArchivo,
                @Param("usuarioModifica") String usuarioModifica);
  
  /**
   * Firma de metodo para actualizar archivo
   * @param correlativoPais: correlativo del pais
   * @return retorna un Long de resultado de exito o error.
   * @see Long.
   */
  Long actualizarArchivoNotificado(@Param("correlativoPais") Long correlativoPais);
  
  /**
   * Firma de metodo para listar archivo y rol
   *
   * @param correlativosArchivo: correlativo de archivo
   * @return retorna un Long de resultado de exito o error.
   * @see Long.
   */
  List<Archivo> listarArchivosRol(@Param("correlativosArchivo") String correlativosArchivo);
  
  /**
   * Firma de metodo para eliminar archivos
   *
   * @param correlativosArchivo: cadena que contiene los correlativos de archivos a eliminar
   * @param usuarioModifica:  usuario que modifica el archivo   
   * @see Long.
   */
  void eliminarArchivos(@Param("correlativosArchivo") String correlativosArchivo,
                        @Param("usuarioModifica") String usuarioModifica);

  /**
   * Firma de metodo para validar el archivo rol
   *
   * @param correlativoArchivo: correlativo de archivo 
   * @param rol: codigo del rol
   * @return retorna un Long de resultado de respuesta.
   * @see Long.
   */
  Long validarArchivoRol(@Param("correlativoArchivo") Long correlativoArchivo,
                    @Param("rol") String rol);
}
