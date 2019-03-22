package com.yanbal.kiosko.common.file;

import org.springframework.web.multipart.MultipartFile;

import com.yanbal.kiosko.common.core.enumeration.TipoArchivo;
/**
 * Interface que contiene la firma de los metodos necesarios para la gestion del archivo
 */
public interface GestionarArchivo {

  /**
   * Firma del metodo que retorna la cadena con la ruta del archivo grabado
   * @param tipo de almacenamiento
   * @param tipoArchivo tipo de archivo
   * @param archivo: archivo que se desea grabar
   * @return valor cadena con la ruta donde se grabo el archivo
   */  
  String grabarArchivo(TipoArchivo tipoArchivo, MultipartFile archivo);

  /**
   * Firma del metodo que elimina el archivo fisico del disco
   * @param tipo de almacenamiento
   * @param tipoArchivo tipo de archivo
   * @param nombreUnico nombre del archivo
   * @return valor booleano que indica el exito de la eliminacion
   */   
  boolean eliminarArchivo(TipoArchivo tipoArchivo, String nombreUnico);

}
