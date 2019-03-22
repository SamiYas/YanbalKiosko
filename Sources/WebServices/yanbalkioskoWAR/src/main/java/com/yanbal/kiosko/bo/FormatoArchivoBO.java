package com.yanbal.kiosko.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.dao.FormatoArchivoDao;
import com.yanbal.kiosko.entities.FormatoArchivo;
import com.yanbal.kiosko.exception.KioskoException;

/**
 * Servicio para la entidad Formato Archivo
 * 
 * @author alex.contreras
 *
 */
@Service
public class FormatoArchivoBO {

  @Autowired
  private FormatoArchivoDao formatoArchivoDao;


  /**
   * Metodo de negocio que devuelve las extensiones y su capacidad para descarga desde los moviles
   * 
   * @return Una de los formatos de archivos
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<FormatoArchivo> listarExtensionTamanho(Long correlativoPais) throws KioskoException {
    return formatoArchivoDao.listarExtensionTamanho(correlativoPais);
  }

  /**
   * Metodo de negocio que devuelve los formato de archivo
   * 
   * @return Una de los formatos de archivos
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<FormatoArchivo> listarUmbrales(Long correlativoPais) throws KioskoException {
    return formatoArchivoDao.listarTodoPorCorrelativo(correlativoPais);
  }

  /**
   * Metodo de negocio que obtiene un formato de archivo segun el correlativo enviado.
   * 
   * @param correlativo el numero de correlativo por el cual se filtrara en la base de datos.
   * @return objFormatoArchivo un objeto entidad Formato Archivo, es el objeto devuelto desde la
   *         base de datos.
   * @see retorna un tipo de dato FormatoArchivo.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public FormatoArchivo obtenerUmbral(Long correlativo) throws KioskoException {
    return formatoArchivoDao.obtenerPorCorrelativo(correlativo);
  }

  /**
   * Metodo de negocio que modifica en la base los datos el formato de archivo. Este metodo maneja
   * la transaccion de base de datos.
   * 
   * @param objFormatoArchivo un objeto entidad Formato Archivo que recibe como parametro para
   *        insertar en la base de datos.
   * @return retorna un valor numerico que indica las filas afectadas en la base de datos.
   * @see retorna un tipo de dato Long.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long actualizarUmbral(FormatoArchivo objFormatoArchivo) {
    return formatoArchivoDao.modificar(objFormatoArchivo);
  }
}
