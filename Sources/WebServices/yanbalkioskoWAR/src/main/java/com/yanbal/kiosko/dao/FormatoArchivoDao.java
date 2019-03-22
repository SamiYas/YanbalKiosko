package com.yanbal.kiosko.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanbal.kiosko.common.core.KioskoDao;
import com.yanbal.kiosko.entities.FormatoArchivo;

/**
 * Interfaz de capa DAO para la entidad FormatoArchivo
 * 
 * @author lennin.davila
 * 
 */
public interface FormatoArchivoDao extends KioskoDao<FormatoArchivo> {
  /**
   * Firma de metodo para obtener la lista de extensiones y valores de descarga validos.
   * 
   * @return retorna un List<FormatoArchivo> con objetos maps que contienen los atributos y valores
   *         de los umbrales.
   * @see List<FormatoArchivo>.
   */
  List<FormatoArchivo> listarExtensionTamanho(@Param("correlativoPais") Long correlativoPais);
}
