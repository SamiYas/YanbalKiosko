package com.yanbal.kiosko.interfaces.rest.servicios;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.reflect.TypeToken;
import com.yanbal.kiosko.common.core.enumeration.YanbalWebServices;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.entities.Pais;
import com.yanbal.kiosko.interfaces.rest.servicios.json.JsonYanbalRequest;
import com.yanbal.kiosko.interfaces.rest.servicios.json.PaisYanbal;
/**
 * Clase Cliente Yanbal para comunicacion con los WS de Yanbal
 * 
 * @author lennin.davila
 * 
 */
public class ClienteYanbal {
  /**
   * Constructor de clase vacio por defecto  
   */ 
  private ClienteYanbal(){    
  }

  /**
   * Metodo estatico para consumir un WS de yanbal
   *
   * @param wsUrl: objeto que contiene la trama a enviar a yanbal
   * @return retorna una cadena con el json de respuesta de yanbal
   * @throws IOException 
   * @throws HttpException 
   * @see String.
   */  
  private static String obtenerJsonYanbal(YanbalWebServices wsUrl) throws HttpException, IOException {
    String responseJson = null;
    String url = wsUrl.getUrl();
    HttpClient client = new HttpClient();
    PostMethod mPost = new PostMethod(url);
    mPost.addParameter("formato", "json");
    mPost.addParameter("trama", wsUrl.getRequest());
    Header mtHeader = new Header();
    mtHeader.setName("content-type");
    mtHeader.setValue("application/x-www-form-urlencoded");
    mPost.addRequestHeader(mtHeader);
    client.executeMethod(mPost);
    responseJson = mPost.getResponseBodyAsString();
    mPost.releaseConnection();
    return responseJson;
  }

  /**
   * Metodo estatico para consumir un WS de yanbal
   *
   * @param wsUrl: objeto que contiene la trama a enviar a yanbal
   * @param request: parametros en json que forman parte del request al servicio de yanbal
   * @return retorna una cadena con el json de respuesta de yanbal
   * @throws IOException 
   * @throws HttpException 
   * @see String.
   */  
  private static String obtenerJsonYanbal(YanbalWebServices wsUrl, String request) throws HttpException, IOException {
    String responseJson = null;
    String url = wsUrl.getUrl();
    HttpClient client = new HttpClient();
    PostMethod mPost = new PostMethod(url);
    mPost.addParameter("formato", "json");
    mPost.addParameter("trama", request);
    Header mtHeader = new Header();
    mtHeader.setName("content-type");
    mtHeader.setValue("application/x-www-form-urlencoded");
    mPost.addRequestHeader(mtHeader);
    client.executeMethod(mPost);
    responseJson = mPost.getResponseBodyAsString();
    mPost.releaseConnection();
    return responseJson;
  }

  /**
   * Metodo estatico para consumir los paises de yanbal
   *
   * @return retorna una lista List<Pais>
   * @throws IOException 
   * @throws HttpException 
   * @see List<Pais>.
   */ 
  public static List<Pais> obtenerPaisesYanbal() throws HttpException, IOException {
    String jsonResponse = obtenerJsonYanbal(YanbalWebServices.LISTADO_PAISES);

    Type tipo = new TypeToken<JsonYanbalRequest>() {}.getType();
    JsonYanbalRequest request = JsonUtil.convertirDeJson(jsonResponse, tipo);

    List<PaisYanbal> listaPaises =
        request.getIntegracionWSResp().getDetalle().getRespuesta().getDatos().getPaises();
    List<Pais> lstpaises = null;
    if (!listaPaises.isEmpty()) {
      lstpaises = new ArrayList<Pais>();
      for (PaisYanbal p : listaPaises) {
        lstpaises.add(new Pais(p.getNombrePais(), p.getNombreCortoPais()));
      }
    }
    return lstpaises;
  }

  /**
   * Metodo estatico para consumir los paises de yanbal
   *
   * @param objJson: objeto que contiene la trama a enviar a yanbal
   * @return retorna una cadena json
   * @throws IOException 
   * @throws HttpException 
   * @throws Exception 
   * @see String.
   */ 
  public static String obtenerDatosUsuario(String objJson) throws HttpException, IOException {
    return obtenerJsonYanbal(YanbalWebServices.DATOS_USUARIO, objJson);
  }

}
