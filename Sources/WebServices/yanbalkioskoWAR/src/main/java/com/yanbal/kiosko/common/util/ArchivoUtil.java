package com.yanbal.kiosko.common.util;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.yanbal.kiosko.common.log.EventoLogBuilder;

/**
 * Clase con los metodos generales para el manejo de archivos
 */
public class ArchivoUtil {

  private ArchivoUtil() {    
  }

  /**
   * Metodo que retorna el archivo a partir de un multpart recibido
   * @param archivoMultipart archivo multipart que se recibe en el request
   * @return archivo de tipo File
   * @throws Exception 
   */  
  public static File obtenerArchivo(MultipartFile archivoMultipart) {

    File archivo = null;
    try {
      archivo = new File(archivoMultipart.getOriginalFilename());
      archivo.createNewFile();
      FileOutputStream fos = new FileOutputStream(archivo);
      fos.write(archivoMultipart.getBytes());
      fos.close();
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(ArchivoUtil.class, ex, null);
    }
    return archivo;
  }

  /**
   * Metodo que retorna una cadena con el nombre unico del archivo autogenerado por el sistema
   * @param nombreOriginal nombre original del archivo fisico
   * @return nombre generado por el sistema para el archivo fisico
   * @throws Exception 
   */   
  public static String obtenerNombreUnico(String nombreOriginal) {
    String nombreArchivo = obtenerNombre(nombreOriginal);
    String extension = obtenerExtension(nombreOriginal);

    final StringBuilder nombreCompuesto = new StringBuilder(nombreArchivo);
    nombreCompuesto.append("-");
    nombreCompuesto.append(System.currentTimeMillis());
    try {
      String nombreUnico = SeguridadUtil.cadenaMD5(nombreCompuesto.toString());
      final StringBuilder nombreArchivoUnico = new StringBuilder(nombreUnico);
      nombreArchivoUnico.append(".");
      nombreArchivoUnico.append(extension);
      return nombreArchivoUnico.toString();
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(ArchivoUtil.class, ex, null);
      return null;
    }
  }

  /**
   * Metodo que retorna la extension del archivo
   * @param nombreArchivo: nombre del archivo
   * @return cadena con la extension del archivo
   */
  public static String obtenerExtension(String nombreArchivo) {
    int indice = nombreArchivo.lastIndexOf(".");
    return nombreArchivo.substring(indice + 1);
  }
  
  /**
   * Metodo que retorna el nombre del archivo sin la extension
   * @param nombreArchivo: nombre del archivo.
   * @return cadena del nombre del archivo
   */
  public static String obtenerNombre(String nombreArchivo) {
    int indice = nombreArchivo.lastIndexOf(".");
    return nombreArchivo.substring(0, indice);
  }

  /**
   * Metodo que retorna la ruta completa del archivo
   * @param rutaNombreArchivo: ruta y nombre del archivo
   * @return cadena con el nombre del archivo y la ruta
   */
  public static String obtenerNombreCompletoDeRuta(String rutaNombreArchivo) {
    int indice = rutaNombreArchivo.lastIndexOf("/");
    return rutaNombreArchivo.substring(indice + 1);
  }

}
