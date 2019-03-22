package com.yanbal.kiosko.common.file;

import org.springframework.web.multipart.MultipartFile;

import com.yanbal.kiosko.common.core.enumeration.TipoAlmacenamiento;
import com.yanbal.kiosko.common.core.enumeration.TipoArchivo;

/**
 * Clase que escribe el archivo en disco
 */
public class FileWriter {

  /**
   * Constructor de clase
   */  
  private FileWriter(){
    
  }
  
  /**
   * Metodo estatico que retorna la cadena con la ruta del archivo grabado
   * @param tipo de almacenamiento
   * @param tipoArchivo tipo de archivo
   * @param archivo: archivo que se desea grabar
   * @return valor cadena con la ruta donde se grabo el archivo
   */
  public static String grabarArchivo(TipoAlmacenamiento tipo, TipoArchivo tipoArchivo,
      MultipartFile archivo) {

    GestionarArchivo procesador;

    switch (tipo) {
      case SERVER_PATH:
        procesador = new ClienteFileSystem();
        return procesador.grabarArchivo(tipoArchivo, archivo);
      case S3:
        // otra instancia
        break;
      default:
        break;
    }
    return "";
  }

  /**
   * Metodo estatico que elimina el archivo fisico del disco
   * @param tipo de almacenamiento
   * @param tipoArchivo tipo de archivo
   * @param nombreUnico nombre del archivo
   * @return valor booleano que indica el exito de la eliminacion
   */  
  public static boolean eliminarArchivo(TipoAlmacenamiento tipo, TipoArchivo tipoArchivo,
      String nombreUnico) {

    GestionarArchivo procesador;

    switch (tipo) {
      case SERVER_PATH:
        procesador = new ClienteFileSystem();
        return procesador.eliminarArchivo(tipoArchivo, nombreUnico);
      case S3:
        // otra instancia
        break;
      default:
        break;
    }
    return false;
  }

}
