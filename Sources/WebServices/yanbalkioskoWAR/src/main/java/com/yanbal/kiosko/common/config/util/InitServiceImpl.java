package com.yanbal.kiosko.common.config.util;

import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.config.init.ModuloInit;
import com.yanbal.kiosko.common.config.init.ModulosInit;
import com.yanbal.kiosko.common.config.loader.ConfigLoader;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.Usuario;

/**
 * Clase que inicializa el plug in init
 * 
 * @author alex.contreras
 * 
 */
@Service
public class InitServiceImpl {

  private static final String CONFIG_PLUGIN_INIT = "WEB-INF/config/init/init-config.xml";

  /**
   * Constructor de la clase, este constructor inicializa los valores del plug init, esto permitira
   * la lectura de las variables del servidor Websphere.
   * 
   * @throws Exception
   * 
   */
  private InitServiceImpl() {
    XStreamFactory factory = new XStreamFactory();
    XStream xstream = factory.buildXStream();
    try {
      xstream.alias("init-modules", ModulosInit.class);
      ModulosInit modulos = (ModulosInit) xstream.fromXML(Helper.toInputStream(CONFIG_PLUGIN_INIT));

      for (ModuloInit modulo : modulos.getModulos()) {
        ConfigLoader inicializador =
            (ConfigLoader) Helper.getInstance(modulo.getClaseInit().trim(), true);
        inicializador.init(modulo.getArchivoConfig().trim());
      }
    } catch (Exception e) {
      Usuario usuario = new Usuario();
      usuario.setNombreUsuario(Application.USUARIO_SISTEMA);
      EventoLogBuilder.obtenerEvento(this.getClass(), e, usuario);
    }
  }

}
