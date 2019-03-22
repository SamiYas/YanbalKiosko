package com.yanbal.kiosko.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.yanbal.kiosko.bo.BitacoraBO;
import com.yanbal.kiosko.bo.DispositivoActivoBO;
import com.yanbal.kiosko.bo.PaisBO;
import com.yanbal.kiosko.bo.SesionWebBO;
import com.yanbal.kiosko.common.core.KioskoControler;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.core.enumeration.Servicio;
import com.yanbal.kiosko.common.json.JsonActualizarPaisDataRequest;
import com.yanbal.kiosko.common.json.JsonActualizarPaisDataResponse;
import com.yanbal.kiosko.common.json.JsonCerrarSesionMovilDataRequest;
import com.yanbal.kiosko.common.json.JsonCerrarSesionMovilDataResponse;
import com.yanbal.kiosko.common.json.JsonCerrarSesionWebDataRequest;
import com.yanbal.kiosko.common.json.JsonCerrarSesionWebDataResponse;
import com.yanbal.kiosko.common.json.JsonInsertarBitacoraDataRequest;
import com.yanbal.kiosko.common.json.JsonInsertarBitacoraDataResponse;
import com.yanbal.kiosko.common.json.JsonListarPaisesDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerUsuarioTokenDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerUsuarioTokenDataResponse;
import com.yanbal.kiosko.common.json.JsonRegistrarSesionMovilDataRequest;
import com.yanbal.kiosko.common.json.JsonRegistrarSesionMovilDataResponse;
import com.yanbal.kiosko.common.json.JsonRegistrarSesionWebDataRequest;
import com.yanbal.kiosko.common.json.JsonRegistrarSesionWebDataResponse;
import com.yanbal.kiosko.common.json.model.JsonBitacora;
import com.yanbal.kiosko.common.json.model.JsonPais;
import com.yanbal.kiosko.common.json.model.JsonResultado;
import com.yanbal.kiosko.common.json.request.JsonKioskoRequest;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.common.util.FechaUtil;
import com.yanbal.kiosko.entities.Bitacora;
import com.yanbal.kiosko.entities.DispositivoActivo;
import com.yanbal.kiosko.entities.Pais;
import com.yanbal.kiosko.entities.SesionWeb;

/**
 * Clase controladora.
 * 
 * @author lennin.davila
 * 
 */
@Controller
public class WsGestionSesionWebControler extends KioskoControler {

  @Autowired
  private PaisBO paisBo;

  @Autowired
  private BitacoraBO bitacoraBo;

  @Autowired
  private SesionWebBO sesionWebBO;

  @Autowired
  private DispositivoActivoBO dispositivoActivoBO;

  /**
   * Devuelve una cadena json con un arreglo de paises
   * 
   * @return cadena json con los paises
   * @throws Exception 
   */
  @RequestMapping(value = "/web/listarPaises", method = RequestMethod.GET)
  @ResponseBody public String listarPaises() throws Exception {
    final String nombreServicio = Servicio.LISTAR_PAISES.getNombre();
    try {
      List<Pais> lista = paisBo.listarPaises();
      JsonListarPaisesDataResponse data = new JsonListarPaisesDataResponse();
      data.setPaises(obtenerJsonPaises(lista));
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que retorna una lista de JsonPais para parsear a json a partir de una lista de Pais
   * 
   * @return Lista JsonPais para parsear a Json
   */
  private List<JsonPais> obtenerJsonPaises(List<Pais> lista) {
    List<JsonPais> listaPaises = new ArrayList<JsonPais>();
    if (lista != null && !lista.isEmpty()) {
      for (Pais pais : lista) {
        JsonPais json = new JsonPais();
        json.setCodigo(pais.getCodigoPais());
        json.setId(pais.getCorrelativoPais());
        json.setNombre(pais.getNombre());
        listaPaises.add(json);
      }
    }
    return listaPaises;
  }

  /**
   * Devuelve una cadena json indicando el exito del registro de la bitacora
   * 
   * @return ResponseBody cadena json
   * @param json cadena json con los atributos para insertar en la base de datos.
   * @throws Exception 
   */
  @RequestMapping(value = "/movil/registrarBitacora", method = RequestMethod.POST)
  @ResponseBody public String registrarBitacora(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.REGISTRAR_BITACORA.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonInsertarBitacoraDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonInsertarBitacoraDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenMovil(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Integer recibido = 0;
      if (request.getBody().getData().getListaBitacora() != null) {
        for (JsonBitacora bitacora : request.getBody().getData().getListaBitacora()) {

          String descripcion = bitacora.getDescripcion();
          String accion = bitacora.getAccion();
          String nombreArchivo = bitacora.getNombreArchivo();
          String tamanhoArchivo = bitacora.getTamanhoArchivo();
          String tipoArchivo = bitacora.getTipoArchivo();
          String tipoDescarga = bitacora.getTipoDescarga();
          String plataforma = bitacora.getPlataforma();
          String dispositivoIdentificador = bitacora.getDispositivoIdentificador();
          Date fechaBitacora = FechaUtil.getFechaFormatoFechaYHora(bitacora.getFechaBitacora());

          Bitacora objBitacora =
              new Bitacora(descripcion, accion, nombreArchivo, tamanhoArchivo, tipoArchivo,
                  tipoDescarga, dispositivo.getNombreUsuario());
          objBitacora.setPlataforma(plataforma);
          objBitacora.setDispositivoIdentificador(dispositivoIdentificador);
          objBitacora.setFecha(fechaBitacora);
          bitacoraBo.insertarBitacora(objBitacora);
        }
        recibido = 1;
      }

      String codigoRespuesta = (recibido != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

      JsonInsertarBitacoraDataResponse data = new JsonInsertarBitacoraDataResponse();
      data.setResultado(codigoRespuesta);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, dispositivo);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json indicando el exito del registro de la sesion movil
   * 
   * @return ResponseEntity cadena json
   * @param json cadena json con los atributos para insertar en la base de datos.
   * @throws Exception 
   */
  @RequestMapping(value = "/movil/registrarsesion", method = RequestMethod.POST)
  @ResponseBody public String registrarsesionmovil(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.REGISTRAR_SESION_MOVIL.getNombre();
    try {
      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonRegistrarSesionMovilDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonRegistrarSesionMovilDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      String usuario = request.getBody().getData().getUsuario();
      String clave = request.getBody().getData().getClave();
      String notificacionId = request.getBody().getData().getIdNotificacion();
      String pais = request.getBody().getData().getPais();
      String dispositivoId = request.getBody().getData().getIdDispositivo();
      String dispositivoSO = request.getBody().getData().getDispositivoSO();

      DispositivoActivo objDispositivoActivo =
          new DispositivoActivo(pais, usuario, clave, notificacionId, dispositivoSO,
              dispositivoId);

      objDispositivoActivo = dispositivoActivoBO.registrarSesion(objDispositivoActivo);

      JsonRegistrarSesionMovilDataResponse data = new JsonRegistrarSesionMovilDataResponse();
      data.setToken(objDispositivoActivo.getToken());
      data.setUsuario(usuario);
      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, dispositivo);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json indicando el exito del registro de la sesion web
   * 
   * @return ResponseEntity cadena json
   * @param json cadena json con los atributos para insertar en la base de datos.
   * @throws Exception 
   */
  @RequestMapping(value = "/web/registrarsesion", method = RequestMethod.POST)
  @ResponseBody public String registrarsesionweb(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.REGISTRAR_SESION_WEB.getNombre();
    try {

      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonRegistrarSesionWebDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonRegistrarSesionWebDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      String usuario = request.getBody().getData().getUsuario();
      Long pais = request.getBody().getData().getPais();
      String clave = request.getBody().getData().getClave();
      String codigoRol = request.getBody().getData().getRol();

      SesionWeb objSesionWeb = new SesionWeb(pais, usuario, clave);
      objSesionWeb = sesionWebBO.registrarSesion(objSesionWeb, codigoRol);

      if (objSesionWeb == null || objSesionWeb.getCorrelativoRol() == null) {
        // retornar error de rol
        return getStringResponse(CodigosError.ERROR_ROL_INEXISTENTE, nombreServicio);
      }

      JsonRegistrarSesionWebDataResponse data = new JsonRegistrarSesionWebDataResponse();
      data.setToken(objSesionWeb.getToken());
      data.setUsuario(usuario);
      data.setResultado(new JsonResultado(objSesionWeb.getCorrelativoRol()));

      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json indicando el exito del registro de la sesion web
   * 
   * @return ResponseEntity cadena json
   * @param json cadena json con los atributos para insertar en la base de datos.
   * @throws Exception 
   */
  @RequestMapping(value = "/web/cerrarsesion", method = RequestMethod.POST)
  @ResponseBody public String cerrarSesionWeb(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.CERRAR_SESION_WEB.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonCerrarSesionWebDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonCerrarSesionWebDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      String token = request.getBody().getData().getToken();

      if (!validarTokenWeb(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      sesionWebBO.cerrarSesion(token);
      JsonCerrarSesionWebDataResponse data = new JsonCerrarSesionWebDataResponse();
      data.setResultado(HTTPSTATUS_OK);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json indicando el exito del registro de la sesion web
   * 
   * @return ResponseEntity cadena json
   * @param json cadena json con los atributos para insertar en la base de datos.
   * @throws Exception 
   */
  @RequestMapping(value = "/movil/cerrarSesion", method = RequestMethod.POST)
  @ResponseBody public String cerrarSesionMovil(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.CERRAR_SESION_MOVIL.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonCerrarSesionMovilDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonCerrarSesionMovilDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();

      if (!validarTokenMovil(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      dispositivoActivoBO.cerrarSesion(token);

      JsonCerrarSesionMovilDataResponse data = new JsonCerrarSesionMovilDataResponse();
      data.setResultado(HTTPSTATUS_OK);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, dispositivo);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Actualiza la lista de los paises con el servicio web de Yanbal
   * 
   * @return cadena json con los paises
   * @throws Exception 
   */
  @RequestMapping(value = "/web/actualizarPais", method = RequestMethod.POST)
  @ResponseBody public String actualizarPais(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.ACTUALIZAR_PAIS.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonActualizarPaisDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonActualizarPaisDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }
      paisBo.actualizarPais();
      JsonActualizarPaisDataResponse data = new JsonActualizarPaisDataResponse();
      data.setResultado(HTTPSTATUS_OK);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que retorna los datos de usuario
   * 
   * @return cadena json con los datos del usuario
   * @throws Exception 
   */
  @RequestMapping(value = "/web/obtenerDatosUsuario", method = RequestMethod.POST)
  @ResponseBody public String obtenerDatosUsuario(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_DATOS_USUARIO.getNombre();
    try {
      return sesionWebBO.obtenerDatosUsuario(objJson);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que retorna los datos de usuario por medio del token de sesion
   * 
   * @return cadena json con los datos del usuario
   * @throws Exception 
   */
  @RequestMapping(value = "/web/obtenerUsuarioToken", method = RequestMethod.POST)
  @ResponseBody public String obtenerUsuarioToken(@RequestBody String objJson) {
    final String nombreServicio = Servicio.OBTENER_USUARIO_TOKEN.getNombre();
    try {
      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonObtenerUsuarioTokenDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonObtenerUsuarioTokenDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();

      if (!validarTokenWeb(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      SesionWeb session = sesionWebBO.obtenerPorToken(token);

      JsonObtenerUsuarioTokenDataResponse data = new JsonObtenerUsuarioTokenDataResponse();
      data.setUsuario(session.getNombreUsuario());
      data.setClave(session.getClave());
      data.setCorrelativoPais(session.getCorrelativoPais());
      data.setPais(session.getPais());
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }
}
