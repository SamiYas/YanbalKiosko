package com.yanbal.kiosko.common.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.config.bean.ApplicationProperties;
import com.yanbal.kiosko.common.config.loader.ApplicationConfigLoader;
import com.yanbal.kiosko.common.core.enumeration.TipoArchivo;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.common.util.ArchivoUtil;
import com.yanbal.kiosko.entities.Usuario;

/**
 * Clase que gestiona el sistema de archivos
 */

@Component
@PropertySource("classpath:properties/kioskov2.properties")
public class ClienteFileSystem implements GestionarArchivo {

  private static final Logger LOG = LoggerFactory.getLogger(ClienteFileSystem.class);
  public static final int BUFFER_SIZE =  1 * 1024;
  ApplicationProperties properties = ApplicationConfigLoader.getInstance()
      .getApplicationProperties();
  String DIRECTORIO_MINIATURA = "";
  String DIRECTORIO_CATALOGOS = "";
  static String DOMINIO = "";
  static String URL_CATALOGO = "";
  static String URL_MINIATURA = "";

  @Autowired
  private Environment env;

  /**
   * Constructor de clase que inicializa los valores de los directorios de guardado
   */
  public ClienteFileSystem() {
    try {
      DIRECTORIO_MINIATURA = properties.getDirectorioMiniatura();
      DIRECTORIO_CATALOGOS = properties.getDirectorioCatalogos();
    } catch (Exception e) {
      Usuario usuario = new Usuario();
      usuario.setNombreUsuario(Application.USUARIO_SISTEMA);
      EventoLogBuilder.obtenerEvento(this.getClass(), e, usuario);
    }
  }

  /**
   * Metodo que inicializa los valores
   */
  @PostConstruct
  public void inicializaValores() {
    DOMINIO = env.getProperty("aplicacion.dominio");
    URL_CATALOGO = env.getProperty("aplicacion.ruta.catalogo");
    URL_MINIATURA = env.getProperty("aplicacion.ruta.miniatura");
  }

  /**
   * Metodo que graba el archivo en una unidad especificada
   * 
   * @param TipoArchivo: esopecifica el tipo de archivo a guardar
   * @param MultipartFile: archivo fisico que se desea guadar
   * @return valor cadena con la ruta de acceso del archivo
   */
  @Override
  public String grabarArchivo(TipoArchivo tipoArchivo, MultipartFile file) {
    if (!file.isEmpty()) {
      try {
        String nombreUnicoArchivo = ArchivoUtil.obtenerNombreUnico(file.getOriginalFilename());        
        InputStream input = file.getInputStream();
        String directorio = "";
        switch (tipoArchivo) {
          case CATALOGO:
            directorio = DIRECTORIO_CATALOGOS;
            break;
          default:
            directorio = DIRECTORIO_MINIATURA;
        }
        String fullPathname = directorio + nombreUnicoArchivo;
        BufferedOutputStream buffStream = 
            new BufferedOutputStream(new FileOutputStream(new File(fullPathname),true),BUFFER_SIZE);        
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = input.read(buffer)) > 0) {
          buffStream.write(buffer, 0, len);
        }
        return obtenerRutaAccesoUnica(tipoArchivo, nombreUnicoArchivo);
      } catch (Exception e) {
        LOG.error("Error al grabar el archivo", e);
        return "";
      }
    } else {
      return "";
    }
  }

  /**
   * Metodo que elimina un archivo especiuficado
   * 
   * @param tipoArchivo: tipo del archivo
   * @param nombreUnico nombre unico del archivo
   * @return valor que indica el exito en la eliminacion del archivo
   */
  @Override
  public boolean eliminarArchivo(TipoArchivo tipoArchivo, String nombreUnico) {
    String directorio = "";
    switch (tipoArchivo) {
      case CATALOGO:
        directorio = DIRECTORIO_CATALOGOS;
        break;
      default:
        directorio = DIRECTORIO_MINIATURA;
    }
    File archivoAEliminar = new File(directorio + nombreUnico);
    if (archivoAEliminar.exists()) {
      archivoAEliminar.delete();
    }
    return true;
  }

  /**
   * Metodo que obtiene la ruta de acceso unica del archivo
   * 
   * @param tipo de archivo.
   * @param nombreArchivo nombre del archivo fisico.
   * @return valor cadena con la ruta unica de acceso
   */
  private String obtenerRutaAccesoUnica(TipoArchivo tipo, String nombreArchivo) {
    StringBuilder rutaFija = new StringBuilder(DOMINIO);
    switch (tipo) {
      case CATALOGO:
        rutaFija.append(URL_CATALOGO);
        break;
      default:
        rutaFija.append(URL_MINIATURA);
    }
    return rutaFija.append(nombreArchivo).toString();
  }

}
