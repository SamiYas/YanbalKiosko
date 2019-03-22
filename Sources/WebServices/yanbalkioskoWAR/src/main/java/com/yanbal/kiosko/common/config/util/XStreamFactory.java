package com.yanbal.kiosko.common.config.util;

import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.Usuario;

/**
 * Clase que configura un objeto Xstream para la obtencion de datos de un arhcivo XML
 * @author alex.contreras
 *
 */
@SuppressWarnings("deprecation")
public class XStreamFactory {

  private static final Logger LOGGER = LogManager.getLogger(XStreamFactory.class);

  /**
   * Metodo inicializado por el servidor Websphere durante el despliegue, este metodo permite interactuar con el websphere para la lectura de variables
   * 
   */ 
  public XStream buildXStream() {
    XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("$", "_"))) {
      protected MapperWrapper wrapMapper(MapperWrapper next) {
        return new MapperWrapper(next) {
          @SuppressWarnings("unchecked")
          public boolean shouldSerializeMember(Class definedIn, String fieldName){
            try {
              return definedIn != Object.class || realClass(fieldName) != null;
            } catch (CannotResolveClassException cnrce) {
              LOGGER.error(cnrce.getMessage(), cnrce);
              try {
                EventoLogBuilder.obtenerEvento(this.getClass(), cnrce, new Usuario(Application.USUARIO_SISTEMA));
              } catch(Exception e) {
                LOGGER.error(e.getMessage(), e);
                if(e!=null)
                  return false;
              }
              return false;
            }
          }
        };
      }
    };
    xStream.autodetectAnnotations(true);
    return xStream;
  }

  /**
   * Metodo inicializado por el servidor Websphere durante el despliegue, este metodo permite interactuar con el websphere para la lectura de variables
   * 
   */     
  public XStream buildRequestXStream() {
    XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("$", "_")));
    xStream.autodetectAnnotations(true);
    return xStream;
  }

  /**
   * Metodo inicializado por el servidor Websphere durante el despliegue, este metodo permite interactuar con el websphere para la lectura de variables
   * 
   */    
  @SuppressWarnings("unchecked")
  public XStream buildXStreamSoap(final String nombreNodoMensaje, final Class T,
      final String prefijo, final String uri) {
    final QNameMap qnameMap = new QNameMap();
    qnameMap.setDefaultPrefix(prefijo);
    final QName qname = new QName(uri, nombreNodoMensaje, prefijo);
    qnameMap.registerMapping(qname, T);
    return new XStream(new StaxDriver(qnameMap));
  }

}
