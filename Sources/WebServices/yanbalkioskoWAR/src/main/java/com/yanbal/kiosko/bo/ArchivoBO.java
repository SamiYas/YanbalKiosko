package com.yanbal.kiosko.bo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yanbal.kiosko.common.core.enumeration.TipoAlmacenamiento;
import com.yanbal.kiosko.common.core.enumeration.TipoArchivo;
import com.yanbal.kiosko.common.file.FileWriter;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.common.util.ArchivoUtil;
import com.yanbal.kiosko.dao.ArchivoDao;
import com.yanbal.kiosko.entities.Archivo;

/**
 * Clase de negocio del Archivo, en esta clase se implementa los metodos para la gestion de los
 * archivos.
 * 
 * @author alex.contreras
 * 
 */
@Service
public class ArchivoBO {

  @Autowired
  private ArchivoDao archivoDao;

  private static final TipoAlmacenamiento TIPO_ALMACENAMIENTO = TipoAlmacenamiento.SERVER_PATH;

  /**
   * Metodo que lista los archivos que pertenecen a una misma coleccion o por nombre y descripcion
   * 
   * @param correlativoColeccion el correlativo de la coleccion
   * @param nombre nombre del archivo
   * @param descripcion descripcion del archivo
   * @return lista de archivos
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Archivo> listarArchivos(Long correlativoColeccion, String nombre, String descripcion,
      Long correlativoPais) {
    return archivoDao.listarArchivos(correlativoColeccion, nombre, descripcion, correlativoPais);
  }

  /**
   * Metodo que obtiene un archivo por su correlativo
   * 
   * @param correlativo correlativo del archivo
   * @return el archivo encontrado
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public Archivo obtenerArchivo(Long correlativo) {
    return archivoDao.obtenerPorCorrelativo(correlativo);
  }

  /**
   * Metodo que elimina un archivo si es que existe
   * 
   * @param hayNuevoArchivo: valor booleano que indica si hay un nuevo archivo
   * @param rutaAccesoArchivo: cadena que indica la ruta del archivo a eliminar
   */
  private void eliminarArchivoSiExiste(boolean hayNuevoArchivo, String rutaAccesoArchivo) {
    if (hayNuevoArchivo) {
      eliminarArchivo(TipoArchivo.CATALOGO, rutaAccesoArchivo);
    }
  }

  /**
   * Metodo que elimina un archivo de vista previa si existiera
   * 
   * @param hayNuevaVistaPrevia: valor booleano que indica si hay un nuevo archivo
   * @param rutaAccesoVistaPrevia: cadena que indica la ruta del archivo a eliminar
   */
  private void eliminarVistaPreviaSiExiste(boolean hayNuevaVistaPrevia, String rutaAccesoVistaPrevia) {
    if (hayNuevaVistaPrevia) {
      eliminarArchivo(TipoArchivo.MINIATURA, rutaAccesoVistaPrevia);
    }
  }

  /**
   * Metodo que elimina un archivo
   * 
   * @param tipoArchivo: valor que indica el tipo de almacenamiento
   * @param rutaAccesoArchivo: cadena que indica la ruta del archivo a eliminar
   */
  private void eliminarArchivo(TipoArchivo tipoArchivo, String rutaAcceso) {
    FileWriter.eliminarArchivo(TIPO_ALMACENAMIENTO, tipoArchivo,
        ArchivoUtil.obtenerNombreCompletoDeRuta(rutaAcceso));
  }

  /**
   * Metodo que actualiza los datos de un archivo
   * 
   * @param objArchivo el archivo con datos actualizados
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public synchronized Long actualizarArchivo(Archivo objArchivo, MultipartFile archivo,
      MultipartFile imagenVistaPrevia) {
    String rutaAccesoArchivoNuevo = "";
    String rutaAccesoImgpreviaNueva = "";
    boolean hayNuevoArchivo = archivo != null;
    boolean hayNuevaVistaPrevia = imagenVistaPrevia != null;

    if (hayNuevoArchivo) {
      rutaAccesoArchivoNuevo =
          FileWriter.grabarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.CATALOGO, archivo);
    }
    if (hayNuevaVistaPrevia) {
      rutaAccesoImgpreviaNueva =
          FileWriter.grabarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.MINIATURA, imagenVistaPrevia);
    }

    try {
      return procesarActualizarArchivo(objArchivo, hayNuevoArchivo, hayNuevaVistaPrevia,
          rutaAccesoArchivoNuevo, rutaAccesoImgpreviaNueva);
    } catch (Exception ex) {

      eliminarArchivoSiExiste(hayNuevoArchivo, rutaAccesoArchivoNuevo);
      eliminarVistaPreviaSiExiste(hayNuevaVistaPrevia, rutaAccesoImgpreviaNueva);

      EventoLogBuilder.obtenerEvento(this.getClass(), ex, null);
      return null;
    }

  }

  /**
   * Metodo que ejecuta la logica de actualizar archivo
   * 
   * @param objArchivo los datos del archivo
   * @param hayNuevoArchivo true si hay cambio de archivo
   * @param hayNuevaVistaPrevia true si hay vista previa
   * @param rutaAccesoArchivoNuevo ruta del nuevo archivo
   * @param rutaAccesoImgpreviaNueva ruta de la nueva imagen previa
   * @return
   */
  private Long procesarActualizarArchivo(Archivo objArchivo, boolean hayNuevoArchivo,
      boolean hayNuevaVistaPrevia, String rutaAccesoArchivoNuevo, String rutaAccesoImgpreviaNueva) {

    Archivo objArchivoActual = archivoDao.obtenerPorCorrelativo(objArchivo.getCorrelativoArchivo());

    if (objArchivoActual == null) {
      return null;
    }

    String rutaAccesoArchivoAnterior = "";
    String rutaAccesoImgpreviaAnterior = "";
    rutaAccesoArchivoAnterior = objArchivoActual.getRutaArchivo();
    rutaAccesoImgpreviaAnterior = objArchivoActual.getRutaImgprevia();

    if (hayNuevoArchivo) {
      objArchivo.setRutaArchivo(rutaAccesoArchivoNuevo);
    } else {
      objArchivo.setRutaArchivo(rutaAccesoArchivoAnterior);
    }

    if (hayNuevaVistaPrevia) {
      objArchivo.setRutaImgprevia(rutaAccesoImgpreviaNueva);
    } else {
      objArchivo.setRutaImgprevia(rutaAccesoImgpreviaAnterior);
    }

    Long codigoRespuesta = archivoDao.modificar(objArchivo);

    Long codigoExito = new Long(1);

    if (codigoExito.equals(codigoRespuesta)) {
      eliminarArchivoSiExiste(hayNuevoArchivo, rutaAccesoArchivoAnterior);
      if (StringUtils.isNotBlank(rutaAccesoImgpreviaAnterior)) {
        eliminarVistaPreviaSiExiste(hayNuevaVistaPrevia, rutaAccesoImgpreviaAnterior);
      }
    } else {
      eliminarArchivoSiExiste(hayNuevoArchivo, rutaAccesoArchivoNuevo);
      eliminarVistaPreviaSiExiste(hayNuevaVistaPrevia, rutaAccesoImgpreviaNueva);
    }

    return codigoRespuesta;
  }

  /**
   * Metodo que cambia la ubicacion del archivo
   * 
   * @param idArchivo id del archivo a mover
   * @param idColeccion id de coleccion a la que sera movido
   * @param usuarioModifica usuario que mueve el archivo
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long moverArchivo(Long correlativoArchivo, Long correlativoColeccion,
      String usuarioModifica) {
    return archivoDao.moverArchivo(correlativoArchivo, correlativoColeccion, usuarioModifica);
  }

  /**
   * Metodo que inserta un archivo en la base de datos y en el repositorio
   * 
   * @param objArchivo objeto de datos del archivo
   * @param archivo el archivo a guardar
   * @param imagenVistaPrevia imagen de vista previa
   * @return codigo de respuesta
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public synchronized Long insertarArchivo(Archivo objArchivo, MultipartFile archivo,
      MultipartFile imagenVistaPrevia) {

    String rutaAccesoArchivoGrabado =
        FileWriter.grabarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.CATALOGO, archivo);
    String rutaAccesoImgpreviaGrabada = "";
    if (imagenVistaPrevia != null) {
      rutaAccesoImgpreviaGrabada =
          FileWriter.grabarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.MINIATURA, imagenVistaPrevia);
    }

    if (StringUtils.isBlank(rutaAccesoArchivoGrabado)) {
      return null;
    }

    try {
      objArchivo.setRutaArchivo(rutaAccesoArchivoGrabado);
      objArchivo.setRutaImgprevia(rutaAccesoImgpreviaGrabada);
      Long codigoRespuesta = archivoDao.insertar(objArchivo);

      Long codigoExito = new Long(1);

      if (!codigoExito.equals(codigoRespuesta)) {
        FileWriter.eliminarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.CATALOGO,
            ArchivoUtil.obtenerNombreCompletoDeRuta(rutaAccesoArchivoGrabado));
        if (imagenVistaPrevia != null) {
          FileWriter.eliminarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.MINIATURA,
              ArchivoUtil.obtenerNombreCompletoDeRuta(rutaAccesoImgpreviaGrabada));
        }
      }
      return codigoRespuesta;
    } catch (Exception ex) {
      FileWriter.eliminarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.CATALOGO,
          ArchivoUtil.obtenerNombreCompletoDeRuta(rutaAccesoArchivoGrabado));
      if (imagenVistaPrevia != null) {
        FileWriter.eliminarArchivo(TIPO_ALMACENAMIENTO, TipoArchivo.MINIATURA,
            ArchivoUtil.obtenerNombreCompletoDeRuta(rutaAccesoImgpreviaGrabada));
      }
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, null);
      return null;
    }

  }

  /**
   * Metodo que elimina un archivo de la base de datos
   * 
   * @param correlativoArchivo codigo del archivo
   * @param usuarioModifica usuario que elimina
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long eliminarArchivo(Long correlativoArchivo, String usuarioModifica) {
    try {
      archivoDao.eliminar(correlativoArchivo, usuarioModifica);
      return 1L;
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, null);
      return null;
    }
  }

  /**
   * Metodo que elimina archivos de la base de datos
   * 
   * @param correlativoArchivo codigo del archivo
   * @param usuarioModifica usuario que elimina
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long eliminarArchivos(String correlativosArchivo, String usuarioModifica) {
    try {
      archivoDao.eliminarArchivos(correlativosArchivo, usuarioModifica);
      return 1L;
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, null);
      return null;
    }
  }

  /**
   * Metodo que cambia el indicador de notificado a verdad.
   * 
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long actualizarArchivoNotificado(Long correlativoPais) {
    return archivoDao.actualizarArchivoNotificado(correlativoPais);
  }

  /**
   * Metodo que lista los archivos con roles
   * 
   * @return codigo de correlativo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Archivo> listarArchivosConRoles(String correlativosArchivo) {
    return archivoDao.listarArchivosRol(correlativosArchivo);
  }

  /**
   * Metodo que validar si un archivo tiene el rol asignado
   * 
   * @param correlativoArchivo correlativo del archivo a validar
   * @param rol codigo del rol
   * @return codigo de respuesta
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public Long validarArchivoRol(Long correlativoArchivo, String rol) {
    return archivoDao.validarArchivoRol(correlativoArchivo, rol);
  }

}
