package com.yanbal.kiosko.common.config.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.config.bean.ApplicationProperties;
import com.yanbal.kiosko.common.config.util.Helper;
import com.yanbal.kiosko.common.config.util.XStreamFactory;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.Usuario;
import com.yanbal.kiosko.exception.KioskoException;

/**
 * Realiza parseo para inicializar archivos xmls usando XStream
 */
@SuppressWarnings("serial")
public class ApplicationConfigLoader implements ConfigLoader, Serializable {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigLoader.class);
  private static final String ERROR_OBJETO_INIT =
      "El objeto de inicializacion no es una instancia de ";
  private static final String EXITO_CONFIG_APPLICATION_PROPERTIES =
      "Bean con propiedades de la aplicacion fue cargado satisfactoriamente.";

  private static ApplicationConfigLoader instancia = new ApplicationConfigLoader();
  private transient ApplicationProperties applicationProperties;

  /**
   * Constructor vacio de clase
   */
  private ApplicationConfigLoader() {}

  /**
   * Metodo que obtiene la instacia actual de ApplicationConfigLoader.
   */
  public static ApplicationConfigLoader getInstance() {
    return instancia;
  }

  /**
   * Metodo init que inicializa los atributos de la clase.
   * 
   * @param obj en Object
   * @throws KioskoException
   */
  public void init(final Object obj) throws KioskoException {
    String fileConfigPath = (String) obj;
    XStreamFactory factory = new XStreamFactory();
    XStream xstream = factory.buildXStream();
    xstream.alias(ApplicationProperties.class.getSimpleName(), ApplicationProperties.class);
    Object xstreamObj = xstream.fromXML(Helper.toInputStream(fileConfigPath));
    if (xstreamObj instanceof ApplicationProperties) {
      applicationProperties = (ApplicationProperties) xstreamObj;
      loadProperties();
      LOG.info(EXITO_CONFIG_APPLICATION_PROPERTIES);
    } else {
      throw new KioskoException(new StringBuilder(ERROR_OBJETO_INIT).append(
          ApplicationConfigLoader.class.getName()).toString());
    }
  }

  /**
   * Metodo quen carga las propiedades desde un archivo de propiedades.
   */
  public void loadProperties() {
    try {
      String retVal = WebsphereVariableLoader.getInstance().getRuta();
      File archivo =
          new File(retVal + File.separator + "config" + File.separator + "application.properties");
      if (archivo.exists()) {
        Properties properties = new Properties();
        properties.load(new FileInputStream(archivo));
        cargarProperties(properties);
      }
    } catch (Exception e) {
      LOG.error("Error en lectura del archivo application.properties: ", e);
    }
  }

  /**
   * Metodo que carga las propiedades
   * 
   * @param properties en Properties
   * @throws Exception
   */
  private void cargarProperties(Properties properties) {
    try {
      applicationProperties.setDirectorioCatalogos(properties.getProperty(applicationProperties
          .getDirectorioCatalogosId()));
      applicationProperties.setDirectorioMiniatura(properties.getProperty(applicationProperties
          .getDirectorioMiniaturaId()));
    } catch (Exception e) {
      Usuario usuario = new Usuario();
      usuario.setNombreUsuario(Application.USUARIO_SISTEMA);
      EventoLogBuilder.obtenerEvento(this.getClass(), e, usuario);
    }
  }

  /**
   * Metodo que obtiene las propiedades de la aplicacion
   */
  public ApplicationProperties getApplicationProperties() {
    return applicationProperties;
  }
}
