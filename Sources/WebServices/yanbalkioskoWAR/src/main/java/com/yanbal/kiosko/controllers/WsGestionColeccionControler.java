package com.yanbal.kiosko.controllers;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;
import com.yanbal.kiosko.bo.ArchivoBO;
import com.yanbal.kiosko.bo.ColeccionBO;
import com.yanbal.kiosko.bo.DispositivoActivoBO;
import com.yanbal.kiosko.bo.FormatoArchivoBO;
import com.yanbal.kiosko.bo.SesionWebBO;
import com.yanbal.kiosko.common.core.KioskoControler;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.core.enumeration.Servicio;
import com.yanbal.kiosko.common.core.enumeration.TipoEntidad;
import com.yanbal.kiosko.common.json.JsonActualizarArchivoDataRequest;
import com.yanbal.kiosko.common.json.JsonActualizarArchivoDataResponse;
import com.yanbal.kiosko.common.json.JsonActualizarColeccionDataRequest;
import com.yanbal.kiosko.common.json.JsonActualizarColeccionDataResponse;
import com.yanbal.kiosko.common.json.JsonActualizarUmbralDataResponse;
import com.yanbal.kiosko.common.json.JsonEliminarArchivosDataRequest;
import com.yanbal.kiosko.common.json.JsonEliminarArchivosDataResponse;
import com.yanbal.kiosko.common.json.JsonEliminarColeccionDataRequest;
import com.yanbal.kiosko.common.json.JsonEliminarColeccionDataResponse;
import com.yanbal.kiosko.common.json.JsonEnviarNotificacionPushDataRequest;
import com.yanbal.kiosko.common.json.JsonEnviarNotificacionPushDataResponse;
import com.yanbal.kiosko.common.json.JsonInsertarArchivoDataRequest;
import com.yanbal.kiosko.common.json.JsonInsertarArchivoDataResponse;
import com.yanbal.kiosko.common.json.JsonInsertarColeccionDataRequest;
import com.yanbal.kiosko.common.json.JsonListarArchivosDataRequest;
import com.yanbal.kiosko.common.json.JsonListarArchivosDataResponse;
import com.yanbal.kiosko.common.json.JsonListarColeccionesDataRequest;
import com.yanbal.kiosko.common.json.JsonListarColeccionesDataResponse;
import com.yanbal.kiosko.common.json.JsonMoverArchivoDataRequest;
import com.yanbal.kiosko.common.json.JsonMoverArchivoDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerArchivoDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerArchivoDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerColeccionDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerColeccionDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerColeccionesArchivosDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerColeccionesArchivosDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerPorNotificarRequest;
import com.yanbal.kiosko.common.json.JsonObtenerPorNotificarResponse;
import com.yanbal.kiosko.common.json.JsonObtenerRutaOrigenDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerRutaOrigenDataResponse;
import com.yanbal.kiosko.common.json.JsonValidarArchivoRolDataRequest;
import com.yanbal.kiosko.common.json.JsonValidarArchivoRolDataResponse;
import com.yanbal.kiosko.common.json.model.JsonArchivo;
import com.yanbal.kiosko.common.json.model.JsonColeccion;
import com.yanbal.kiosko.common.json.model.JsonRuta;
import com.yanbal.kiosko.common.json.model.JsonUmbral;
import com.yanbal.kiosko.common.json.request.JsonKioskoRequest;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.common.util.FechaUtil;
import com.yanbal.kiosko.entities.Archivo;
import com.yanbal.kiosko.entities.Coleccion;
import com.yanbal.kiosko.entities.DispositivoActivo;
import com.yanbal.kiosko.entities.FormatoArchivo;
import com.yanbal.lib.corp.entities.TokenPush;
import com.yanbal.lib.corp.enums.Plataforma;
import com.yanbal.lib.corp.notificaciones.ClientePush;

/**
 * Clase controladora de Gestion de Coleccion
 * 
 * @author alex.contreras
 * 
 */
@Controller
@PropertySource("classpath:properties/kioskov2.properties")
public class WsGestionColeccionControler extends KioskoControler {

  private static final Long ERROR_INSERCION_COLECCION = 2L;
  private static final Long ERROR_INSERCION_ARCHIVO = 2L;
  private static final Long ERROR_ACTUALIZACION_ARCHIVO = 2L;
  private static final Long ERROR_MOVER_ARCHIVO = 2L;
  private static final Long ERROR_VALIDAR_ROL = 0L;
  private static String DOMINIO_LECTURA = "";
  private static String DOMINIO = "";

  @Autowired
  private ColeccionBO coleccionBO;

  @Autowired
  private ArchivoBO archivoBO;

  @Autowired
  private FormatoArchivoBO formatoArchivoBO;

  @Autowired
  private DispositivoActivoBO dispositivoActivoBO;

  @Autowired
  private SesionWebBO sesionWebBO;

  @Autowired
  private Environment env;

  
  public WsGestionColeccionControler() {
  }
  
  @PostConstruct
  public void inicializarWsGestionColeccionControler(){
    DOMINIO_LECTURA = env.getProperty("aplicacion.dominio.lectura"); 
    DOMINIO = env.getProperty("aplicacion.dominio");
  }
  
  /**
   * Devuelve una cadena json con las colecciones y archivos
   * 
   * @return cadena json con las colecciones y archivos
   * @throws Exception 
   */
  @RequestMapping(value = "/movil/obtenerColeccionesArchivos", method = RequestMethod.POST)
  @ResponseBody public String obtenerColeccionesArchivos(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_COLECCIONES_ARCHIVOS.getNombre();
    try {
      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonObtenerColeccionesArchivosDataRequest>>() {} .getType();
      JsonKioskoRequest<JsonObtenerColeccionesArchivosDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();
      String pais = request.getBody().getData().getPais();
      String codigoRol = request.getBody().getData().getRol();
      String sfechaSincronizacion = request.getBody().getData().getFecha_sincronizacion();
      if (!validarTokenMovil(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Date fechaSincronizacion = FechaUtil.getFechaFormatoFechaYHora(sfechaSincronizacion);

      List<HashMap<String, Object>> lista =
          coleccionBO.obtenerColeccionesArchivos(pais, codigoRol, fechaSincronizacion);

      if (!lista.isEmpty()) {
        Map map = lista.get(0);
        sfechaSincronizacion = map.get("FECHASINCRONIZACION").toString();
      }

      List<JsonUmbral> umbrales =
          obtenerFormatoArchivoJsonDeFormatoArchivo(formatoArchivoBO.listarExtensionTamanho(dispositivo.getCorrelativoPais()));

      JsonObtenerColeccionesArchivosDataResponse data =
          new JsonObtenerColeccionesArchivosDataResponse();
      data.setColecciones(lista);
      data.setUmbrales(umbrales);
      data.setFechaSincronizacion(sfechaSincronizacion);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, dispositivo);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que retorna el response de sesion invalida
   * @param nombreServicio de la sesion.
   * @return valor cadena con el response de respuesta
   */  
  private List<JsonUmbral> obtenerFormatoArchivoJsonDeFormatoArchivo(
      List<FormatoArchivo> formatoArchivos) {
    List<JsonUmbral> listaJson = new ArrayList<JsonUmbral>();
    if (formatoArchivos != null && !formatoArchivos.isEmpty()) {
      for (FormatoArchivo formatoArchivo : formatoArchivos) {
        JsonUmbral json = new JsonUmbral();
        json.setExtension(formatoArchivo.getExtension());
        json.setDescarga(formatoArchivo.getDescarga());
        listaJson.add(json);
      }
    }
    return listaJson;
  }

  /**
   * Devuelve una cadena json con un arreglo de colecciones que pertenecen a una coleccion padre
   * 
   * @return cadena json con las colecciones
   * @throws Exception 
   */
  @RequestMapping(value = "/web/listarColecciones", method = RequestMethod.POST)
  @ResponseBody public String listarColecciones(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.LISTAR_COLECCIONES.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonListarColeccionesDataRequest>>() {}.getType();

      JsonKioskoRequest<JsonListarColeccionesDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      Long correlativoColeccionPadre = request.getBody().getData().getIdPadre();
      Long correlativoPais = request.getBody().getData().getPais();

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      List<Coleccion> colecciones = coleccionBO.listarColecciones(correlativoColeccionPadre, correlativoPais);

      List<JsonColeccion> lista = new ArrayList<JsonColeccion>();

      if (colecciones != null) {
        for (Coleccion coleccion : colecciones) {
          JsonColeccion json = new JsonColeccion();
          json.setDescripcion(coleccion.getDescripcion());
          json.setId(coleccion.getCorrelativoColeccion());
          json.setIdPadre(coleccion.getCorrelativoColeccionPadre());
          json.setNivel(coleccion.getNivel());
          json.setNombre(coleccion.getNombre());
          json.setOrden(coleccion.getNroOrden());
          json.setColeccionesHijas(coleccion.getCantidadColeccionesHijas());
          json.setTipoUsuario(coleccion.getCorrelativoRol());
          lista.add(json);
        }
      }

      JsonListarColeccionesDataResponse data = new JsonListarColeccionesDataResponse();
      data.setColecciones(lista);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json con la respuesta de insertar una nueva coleccion
   * 
   * @return cadena json con la respuesta
   * @throws Exception 
   */
  @RequestMapping(value = "/web/insertarColeccion", method = RequestMethod.POST)
  @ResponseBody public String insertarColeccion(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.INSERTAR_COLECCION.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonInsertarColeccionDataRequest>>() {}.getType();

      JsonKioskoRequest<JsonInsertarColeccionDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idPadre = request.getBody().getData().getIdPadre();
      Long idPais = request.getBody().getData().getPais();
      String descripcion = request.getBody().getData().getDescripcion();
      String color = request.getBody().getData().getColor();
      Integer nivel = request.getBody().getData().getNivel();
      String nombre = request.getBody().getData().getNombre();
      Integer nroOrden = request.getBody().getData().getOrden();

      Coleccion coleccion =
          new Coleccion(idPadre, idPais, sesion.getCorrelativoRol(), nombre,
              nivel, nroOrden, descripcion);
      coleccion.setColor(color);
      coleccion.setUsuarioRegistra(sesion.getNombreUsuario());
      Long filas = coleccionBO.insertarColeccion(coleccion);

      if (ERROR_INSERCION_COLECCION.equals(filas)) {
        return getStringResponse(CodigosError.ERROR_NOMBRE_DUPLICADO, nombreServicio);
      }

      String resultado = (filas != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

      JsonActualizarUmbralDataResponse data = new JsonActualizarUmbralDataResponse();
      data.setResultado(resultado);

      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json con la respuesta de eliminar una coleccion
   * 
   * @return cadena json con la respuesta
   * @throws Exception 
   */
  @RequestMapping(value = "/web/eliminarColeccion", method = RequestMethod.POST)
  @ResponseBody public String eliminarColeccion(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.ELIMINAR_COLECCION.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonEliminarColeccionDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonEliminarColeccionDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idColeccion = request.getBody().getData().getId();

      // validar privilegios
      if (!validarPrivilegios(idColeccion, TipoEntidad.COLECCION)) {
        return getStringResponse(CodigosError.ERROR_PRIVILEGIOS, nombreServicio);
      }

      Long codigoRespuesta = coleccionBO.eliminarColeccion(idColeccion, sesion.getNombreUsuario());

      JsonEliminarColeccionDataResponse data = new JsonEliminarColeccionDataResponse();
      data.setResultado(HTTPSTATUS_OK);
      data.setCodigoRespuesta(codigoRespuesta);

      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json con una coleccion
   * 
   * @return cadena json con los datos de la coleccion
   * @throws Exception 
   */  
  @RequestMapping(value = "/web/obtenerColeccion", method = RequestMethod.POST)
  @ResponseBody public String obtenerColeccion(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_COLECCION.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonObtenerColeccionDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonObtenerColeccionDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idColeccion = request.getBody().getData().getId();

      Coleccion objObtenido = coleccionBO.obtenerColeccion(idColeccion);

      if (objObtenido == null) {
        objObtenido = new Coleccion();
      }

      JsonObtenerColeccionDataResponse data = new JsonObtenerColeccionDataResponse();
      data.setDescripcion(objObtenido.getDescripcion());
      data.setId(objObtenido.getCorrelativoColeccion());
      data.setIdPadre(objObtenido.getCorrelativoColeccionPadre());
      data.setNombreColeccionPadre(objObtenido.getNombreColeccionPadre());
      data.setNivel(objObtenido.getNivel());
      data.setNombre(objObtenido.getNombre());
      data.setOrden(objObtenido.getNroOrden());
      data.setColor(objObtenido.getColor());

      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Actualiza una coleccion
   * 
   * @return cadena json con el resultado de la actualizacion
   * @throws Exception 
   */   
  @RequestMapping(value = "/web/actualizarColeccion", method = RequestMethod.POST)
  @ResponseBody public String actualizarColeccion(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.ACTUALIZAR_COLECCION.getNombre();
    try {

      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonActualizarColeccionDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonActualizarColeccionDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      return procesarActualizarColeccion(request.getBody().getData(), nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que se encarga de procesar la logica del servicio de Actualizar Coleccion
   * @param dataRequest los datos para procesar
   * @param nombreServicio el nombre del servicio usado
   * @return la respuesta del proceso
   */
  private String procesarActualizarColeccion(JsonActualizarColeccionDataRequest dataRequest, String nombreServicio){
    String descripcion = dataRequest.getDescripcion();
    String color = dataRequest.getColor();
    Long id = dataRequest.getId();
    Long idPadre = dataRequest.getIdPadre();
    Integer nivel = dataRequest.getNivel();
    String nombre = dataRequest.getNombre();
    Integer nroOrden = dataRequest.getOrden();

    // validar privilegios
    if (!validarPrivilegios(id, TipoEntidad.COLECCION)) {
      return getStringResponse(CodigosError.ERROR_PRIVILEGIOS, nombreServicio);
    }

    Coleccion coleccion = new Coleccion(id, idPadre, nombre, nivel, nroOrden, descripcion, color);
    coleccion.setUsuarioModifica(sesion.getNombreUsuario());
    // actualizar rol
    coleccion.setCorrelativoRol(sesion.getCorrelativoRol());
    Long codigoRespuesta = coleccionBO.actualizarColeccion(coleccion);

    if (ERROR_INSERCION_COLECCION.equals(codigoRespuesta)) {
      return getStringResponse(CodigosError.ERROR_NOMBRE_DUPLICADO, nombreServicio);
    }

    String resultado = (codigoRespuesta != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

    JsonActualizarColeccionDataResponse data = new JsonActualizarColeccionDataResponse();
    data.setResultado(resultado);
    return getStringResponse(data, nombreServicio);
  }

  /**
   * lista los archivos
   * 
   * @return cadena json con la lista de archivos
   * @throws Exception 
   */   
  @RequestMapping(value = "/web/listarArchivos", method = RequestMethod.POST)
  @ResponseBody public String listarArchivos(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.LISTAR_ARCHIVOS.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonListarArchivosDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonListarArchivosDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      String descripcion = request.getBody().getData().getDescripcion();
      Long idColeccion = request.getBody().getData().getIdColeccion();
      String nombre = request.getBody().getData().getNombre();
      Long idPais = request.getBody().getData().getPais();

      List<Archivo> archivos = archivoBO.listarArchivos(idColeccion, nombre, descripcion, idPais);

      JsonListarArchivosDataResponse data = new JsonListarArchivosDataResponse();
      data.setArchivos(obtenerArchivosJsonDeArchivos(archivos));

      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Obtiene los datos de un archivo
   * 
   * @return cadena json con los datos de un archivo
   * @throws Exception 
   */  
  @RequestMapping(value = "/web/obtenerArchivo", method = RequestMethod.POST)
  @ResponseBody public String obtenerArchivo(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_ARCHIVO.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonObtenerArchivoDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonObtenerArchivoDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idArchivo = request.getBody().getData().getId();

      Archivo objArchivo = archivoBO.obtenerArchivo(idArchivo);

      if (objArchivo == null) {
        objArchivo = new Archivo();
      }

      JsonObtenerArchivoDataResponse data = new JsonObtenerArchivoDataResponse();
      data.setDescargable(objArchivo.getDescargable());
      data.setDescripcion(objArchivo.getDescripcion());
      data.setDestacado(objArchivo.getDestacado());
      data.setExtension(objArchivo.getExtension());
      String fechaCaducidad = FechaUtil.getFechaFormatoFecha(objArchivo.getFechaCaducidad());
      data.setFechaCaducidad(fechaCaducidad);
      String fechaInicio = FechaUtil.getFechaFormatoFecha(objArchivo.getFechaInicio());
      data.setFechaInicio(fechaInicio);
      data.setIdArchivo(objArchivo.getCorrelativoArchivo());
      data.setIdColeccion(objArchivo.getCorrelativoColeccion());
      data.setIdPais(objArchivo.getPais());
      data.setNombre(objArchivo.getNombre());
      data.setNroOrden(objArchivo.getNroOrden());
      data.setRutaArchivo(objArchivo.getRutaArchivo());
      data.setRutaImagenPrevia(objArchivo.getRutaImgprevia());
      data.setTamanho((objArchivo.getTamanho() != null && !objArchivo.getTamanho().trim().isEmpty()) ? Double
          .valueOf(objArchivo.getTamanho()) : null);
      data.setRoles(objArchivo.getRoles());
      data.setEstado(objArchivo.getEstado());

      data.setRutaArchivo(obtenerRutaInterna(data.getRutaArchivo()));
      data.setRutaImagenPrevia(obtenerRutaInterna(data.getRutaImagenPrevia()));

      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  
  /**
   * Actualiza un archivo
   * 
   * @return cadena json con los datos del resultado de la actualizacion del archivo
   * @throws Exception 
   */
  @RequestMapping(value = "/web/actualizarArchivo", method = RequestMethod.POST)
  @ResponseBody public String actualizarArchivo(
      @RequestParam(value = "archivo", required = false) MultipartFile archivoAGuardar,
      @RequestParam(value = "vistaPrevia", required = false) MultipartFile imagenPreviaAGuardar,
      @RequestParam(value = "json") String objJson) throws Exception {
    final String nombreServicio = Servicio.ACTUALIZAR_ARCHIVO.getNombre();
    try {

      if (imagenPreviaAGuardar != null && imagenPreviaAGuardar.getSize() <=0) {
        imagenPreviaAGuardar = null;
      }
      if (archivoAGuardar != null && archivoAGuardar.getSize() <=0) {
          archivoAGuardar = null;
      }
      Type tipo = new TypeToken<JsonKioskoRequest<JsonActualizarArchivoDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonActualizarArchivoDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      return procesarActualizarArchivo(request.getBody().getData(), nombreServicio, archivoAGuardar, imagenPreviaAGuardar);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que se encarga de procesar la logica del servicio Actualizar Archivo
   * @param dataRequest los datos usados para procesar
   * @param nombreServicio el nombre del servicio usado
   * @param archivoAGuardar archivo a guardar
   * @param imagenPreviaAGuardar imagen a guardar
   * @return respuesta del proceso
   */
  private String procesarActualizarArchivo(JsonActualizarArchivoDataRequest dataRequest, String nombreServicio, MultipartFile archivoAGuardar, MultipartFile imagenPreviaAGuardar) {
    Long idArchivo = dataRequest.getIdArchivo();
    Integer descargable = dataRequest.getDescargable();
    String descripcion = dataRequest.getDescripcion();
    Integer destacado = dataRequest.getDestacado();
    String fechaCaducidad = dataRequest.getFechaCaducidad();
    String fechaInicio = dataRequest.getFechaInicio();
    String nombre = dataRequest.getNombre();
    Integer nroOrden = dataRequest.getNroOrden();
    String roles = dataRequest.getRoles();
    Double tamanho = dataRequest.getTamanho();
    String extension = dataRequest.getExtension();

    // validar privilegios
    if (!validarPrivilegios(idArchivo, TipoEntidad.ARCHIVO)) {
      return getStringResponse(CodigosError.ERROR_PRIVILEGIOS, nombreServicio);
    }

    Archivo objArchivo = new Archivo();
    objArchivo.setCorrelativoArchivo(idArchivo);
    objArchivo.setDescargable(descargable);
    objArchivo.setDescripcion(descripcion);
    objArchivo.setDestacado(destacado);
    objArchivo.setFechaCaducidad(FechaUtil.getFechaFormatoFecha(fechaCaducidad));
    objArchivo.setFechaInicio(FechaUtil.getFechaFormatoFecha(fechaInicio));
    objArchivo.setNombre(nombre);
    objArchivo.setNroOrden(nroOrden);
    objArchivo.setRoles(roles);
    objArchivo.setTamanho(String.valueOf(tamanho));
    objArchivo.setExtension(extension);
    objArchivo.setUsuarioModifica(sesion.getNombreUsuario());
    // actualizar rol
    objArchivo.setCorrelativoRol(sesion.getCorrelativoRol());

    Long codigoRespuesta =
        archivoBO.actualizarArchivo(objArchivo, archivoAGuardar, imagenPreviaAGuardar);

    if (ERROR_ACTUALIZACION_ARCHIVO.equals(codigoRespuesta)) {
      return getStringResponse(CodigosError.ERROR_NOMBRE_DUPLICADO, nombreServicio);
    }

    String resultado = (codigoRespuesta != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

    JsonActualizarArchivoDataResponse data = new JsonActualizarArchivoDataResponse();
    data.setResultado(resultado);
    return getStringResponse(data, nombreServicio);
  }

  /**
   * Metodo que obtiene los archisvos en una lista para parsear a json desde un lista de la entidad de archivos
   * 
   * @return Objeto lista con los archivos a parsear a json
   */  
  private List<JsonArchivo> obtenerArchivosJsonDeArchivos(List<Archivo> archivos) {
    List<JsonArchivo> listaJson = new ArrayList<JsonArchivo>();
    if (archivos != null && !archivos.isEmpty()) {
      for (Archivo archivo : archivos) {
        JsonArchivo json = new JsonArchivo();
        json.setDescargable(archivo.getDescargable());
        json.setDescripcion(archivo.getDescripcion());
        json.setDestacado(archivo.getDestacado());
        json.setExtension(archivo.getExtension());
        String fechaCaducidad = FechaUtil.getFechaFormatoFecha(archivo.getFechaCaducidad());
        json.setFechaCaducidad(fechaCaducidad);
        String fechaInicio = FechaUtil.getFechaFormatoFecha(archivo.getFechaInicio());
        json.setFechaInicio(fechaInicio);
        json.setIdArchivo(archivo.getCorrelativoArchivo());
        json.setIdColeccion(archivo.getCorrelativoColeccion());
        json.setIdPais(archivo.getPais());
        json.setNombre(archivo.getNombre());
        json.setNroOrden(archivo.getNroOrden());
        json.setRutaArchivo(archivo.getRutaArchivo());
        json.setRutaImagenPrevia(archivo.getRutaImgprevia());
        json.setTamanho((archivo.getTamanho() != null && !archivo.getTamanho().trim().isEmpty()) ? Double
            .valueOf(archivo.getTamanho()) : null);
        json.setEstado(archivo.getEstado());
        json.setRoles(archivo.getRoles());
        json.setTipoUsuario(archivo.getCorrelativoRol());

        json.setRutaArchivo(obtenerRutaInterna(json.getRutaArchivo()));
        json.setRutaImagenPrevia(obtenerRutaInterna(json.getRutaImagenPrevia()));
        
        listaJson.add(json);
      }
    }
    return listaJson;
  }

  /**
   * Metodo que obtiene la ruta origen de un archivo
   * 
   * @return Cadena con la ruta logica donde se encuentra un archivo
   * @throws Exception 
   */  
  @RequestMapping(value = "/web/obtenerRutaOrigen", method = RequestMethod.POST)
  @ResponseBody public String obtenerRutaOrigen(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_RUTA_ORIGEN.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonObtenerRutaOrigenDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonObtenerRutaOrigenDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idColeccion = request.getBody().getData().getId();

      List<Coleccion> colecciones = coleccionBO.obtenerRutaOrigen(idColeccion);

      JsonObtenerRutaOrigenDataResponse data = new JsonObtenerRutaOrigenDataResponse();
      data.setRutas(obtenerRutaJsonDeColecciones(colecciones));
      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que permite mover un archivo de una coleccion a otra
   * 
   * @return Cadena con la ruta logica donde se encuentra un archivo
   * @throws Exception 
   */   
  @RequestMapping(value = "/web/moverArchivo", method = RequestMethod.POST)
  @ResponseBody public String moverArchivo(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.MOVER_ARCHIVO.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonMoverArchivoDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonMoverArchivoDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long idArchivo = request.getBody().getData().getId();
      Long idColeccion = request.getBody().getData().getIdColeccion();

      Long codigoRespuesta = archivoBO.moverArchivo(idArchivo, idColeccion, sesion.getNombreUsuario());

      if (ERROR_MOVER_ARCHIVO.equals(codigoRespuesta)) {
        return getStringResponse(CodigosError.ERROR_NOMBRE_DUPLICADO, nombreServicio);
      }

      String resultado = (codigoRespuesta != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

      JsonMoverArchivoDataResponse data = new JsonMoverArchivoDataResponse();
      data.setResultado(resultado);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que permite insertar un archivo
   * @param archivoAGuardar: archivo a guardar
   * @param imagenPreviaAGuardar: Archivo de imagen previa (opcional)
   * @param objJson: Cadena json del request
   * @return Cadena json con el resultado de la insercion del archivo
   * @throws Exception 
   */    
  @RequestMapping(value = "/web/insertarArchivo", method = RequestMethod.POST)
  @ResponseBody public String insertarArchivo(
      @RequestParam(value = "archivo") MultipartFile archivoAGuardar, 
      @RequestParam(value = "vistaPrevia", required = false) MultipartFile imagenPreviaAGuardar,
      @RequestParam(value = "json") String objJson) throws Exception {
    final String nombreServicio = Servicio.INSERTAR_ARCHIVO.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonInsertarArchivoDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonInsertarArchivoDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Integer descargable = request.getBody().getData().getDescargable();
      String descripcion = request.getBody().getData().getDescripcion();
      Integer destacado = request.getBody().getData().getDestacado();
      String extension = request.getBody().getData().getExtension();
      String fechaCaducidad = request.getBody().getData().getFechaCaducidad();
      String fechaInicio = request.getBody().getData().getFechaInicio();
      Long idColeccion = request.getBody().getData().getIdColeccion();
      Long idPais = request.getBody().getData().getPais();
      String nombre = request.getBody().getData().getNombre();
      Integer nroOrden = request.getBody().getData().getNroOrden();
      Double tamanho = request.getBody().getData().getTamanho();
      String roles = request.getBody().getData().getRoles();

      Archivo objArchivo = new Archivo();
      objArchivo.setCorrelativoColeccion(idColeccion);
      objArchivo.setCorrelativoPais(idPais);
      objArchivo.setCorrelativoRol(sesion.getCorrelativoRol());
      objArchivo.setDescargable(descargable);
      objArchivo.setDescripcion(descripcion);
      objArchivo.setDestacado(destacado);
      objArchivo.setExtension(extension);
      objArchivo.setFechaCaducidad(FechaUtil.getFechaFormatoFecha(fechaCaducidad));
      objArchivo.setFechaInicio(FechaUtil.getFechaFormatoFecha(fechaInicio));
      objArchivo.setNombre(nombre);
      objArchivo.setNroOrden(nroOrden);
      objArchivo.setTamanho(String.valueOf(tamanho));
      objArchivo.setRoles(roles);
      objArchivo.setUsuarioRegistra(sesion.getNombreUsuario());

      Long codigoRespuesta =
          archivoBO.insertarArchivo(objArchivo, archivoAGuardar, imagenPreviaAGuardar);

      if (ERROR_INSERCION_ARCHIVO.equals(codigoRespuesta)) {
        return getStringResponse(CodigosError.ERROR_NOMBRE_DUPLICADO, nombreServicio);
      }

      String resultado = (codigoRespuesta != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;
      JsonInsertarArchivoDataResponse data = new JsonInsertarArchivoDataResponse();
      data.setResultado(resultado);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que obtiene las ruta logica a partir de un objeto lista con las colecciones
   * @param colecciones: obj que contiene uno de los elementos de la ruta  
   * @return Objeto lista para parsear a un json con los elementos de la ruta
   */  
  private List<JsonRuta> obtenerRutaJsonDeColecciones(List<Coleccion> colecciones) {
    List<JsonRuta> listaJson = new ArrayList<JsonRuta>();
    if (colecciones != null && !colecciones.isEmpty()) {
      for (Coleccion coleccion : colecciones) {
        JsonRuta ruta = new JsonRuta();
        ruta.setId(coleccion.getCorrelativoColeccion());
        ruta.setNivel(coleccion.getNivel());
        ruta.setNombre(coleccion.getNombre());
        listaJson.add(ruta);
      }
    }
    return listaJson;
  }

  /**
   * Metodo que permite eliminar archivos de manera multiple
   * @param objJson: objeto que contiene los archivos que se desean eliminar
   * @return Cadena json con el resultado de la eliminacion de los archivos
   * @throws Exception 
   */  
  @RequestMapping(value = "/web/eliminarArchivos", method = RequestMethod.POST)
  @ResponseBody public String eliminarArchivos(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.ELIMINAR_ARCHIVOS.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonEliminarArchivosDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonEliminarArchivosDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      return procesarEliminarArchivos(request.getBody().getData(), nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que se encarga de procesar la logica del servicio Eliminar Archivos 
   * @param dataRequest los datos para procesar
   * @param nombreServicio el nombre del servicio usado
   * @return respuesta del proceso 
   */
  private String procesarEliminarArchivos(JsonEliminarArchivosDataRequest dataRequest, String nombreServicio) {
    JsonEliminarArchivosDataResponse data = new JsonEliminarArchivosDataResponse();
    List<Long> listaIdsArchivo = dataRequest.getListaArchivos();
    String idsArchivos = obtenerIdsArchivosDeLista(listaIdsArchivo);
    if (idsArchivos.isEmpty()) {
      return getStringResponse(CodigosError.ERROR_DATOS_INSUFICIENTES, nombreServicio);
    }

    // validar privilegios
    if (!esUsuarioCorp()) {
      List<String> archivosSinPrivilegios = obtenerArchivosSinPrivilegios(idsArchivos);
      if (archivosSinPrivilegios != null && !archivosSinPrivilegios.isEmpty()) {
        data.setArchivos(archivosSinPrivilegios);
        return getStringResponse(CodigosError.ERROR_PRIVILEGIOS, data, nombreServicio);
      }
    }

    Long codigoRespuesta = archivoBO.eliminarArchivos(idsArchivos, sesion.getNombreUsuario());
    String resultado = (codigoRespuesta != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

    data.setResultado(resultado);
    return getStringResponse(data, nombreServicio);
  }
  
  /**
   * Metodo que retorna una lista de cadenas a partir de una cadena
   * @param idsArchivos: cadema que contiene los codigos de los archivos
   * @return objeto lista con las codigos de los archivos
   */   
  private List<String> obtenerArchivosSinPrivilegios(String idsArchivos) {
    List<Archivo> archivosConRoles = archivoBO.listarArchivosConRoles(idsArchivos);
    List<String> archivosSinPrivilegios = new ArrayList<String>();
    if (archivosConRoles != null && !archivosConRoles.isEmpty()) {
      for (Archivo archivo : archivosConRoles) {
        if (!validarRol(archivo.getCorrelativoRol())) {
          archivosSinPrivilegios.add(archivo.getNombre());
        }
      }
    }
    return archivosSinPrivilegios;
  }

  /**
   * Metodo que retorna una cadena a partir de una lista de codigos de archivo
   * @param listaIdsArchivo: lista de codigos de archivo
   * @return cadena que contiene la lista de codigos separados por ","
   */   
  private String obtenerIdsArchivosDeLista(List<Long> listaIdsArchivo) {
    String idsArchivos = "";
    if (listaIdsArchivo != null && !listaIdsArchivo.isEmpty()) {
      for (Long id : listaIdsArchivo) {
        idsArchivos = idsArchivos + String.valueOf(id) + ",";
      }
    }
    if (!idsArchivos.isEmpty()) {
      idsArchivos = idsArchivos.substring(0, idsArchivos.length() - 1);
    }
    return idsArchivos;
  }

  /**
   * Metodo que envia notificaciones push a los diferentes plataformas de dispositivos 
   * @return cadena que contiene el resultado del envio.
   * @throws Exception 
   */
  @RequestMapping(value = "/web/enviarNotificacionPush", method = RequestMethod.POST)
  @ResponseBody public String enviarNotificacionPush(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.ENVIAR_NOTIFICACION_PUSH.getNombre();
    try {
      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonEnviarNotificacionPushDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonEnviarNotificacionPushDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();
      Properties properties = new Properties();
      int diasInactividad = 30;
      String datasourcename = "";

      InputStream propertiesInputStream =
          this.getClass().getClassLoader().getResourceAsStream("properties/kioskov2.properties");
      properties.load(propertiesInputStream);
      diasInactividad = Integer.parseInt(properties.getProperty("kioskov2.DiasInactividad"));
      datasourcename = properties.getProperty("kioskov2.jndi_esquema_corporativo");

      if (!validarTokenWeb(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }
      String mensaje = request.getBody().getData().getMensaje();
      Long idPais = request.getBody().getData().getPais();
      List<DispositivoActivo> tokens =
          dispositivoActivoBO.obtenerDispositivos(idPais, diasInactividad);

      List<TokenPush> tokenPush = new ArrayList<TokenPush>();
      for (DispositivoActivo dispositivo : tokens) {
        Plataforma plataforma;
        if (dispositivo.getDispositivoSis().equals("IOS_PLATFORM")) {
          plataforma = Plataforma.IOS_PLATFORM;
        } else if (dispositivo.getDispositivoSis().equals("ANDROID_PLATFORM")) {
          plataforma = Plataforma.ANDROID_PLATFORM;
        } else {
          plataforma = Plataforma.WINDOWS_PLATFORM;
        }
        tokenPush.add(new TokenPush(dispositivo.getNotificacionIdentificador(), plataforma));
      }
      ClientePush push = new ClientePush();
      push.notificar(tokenPush, idPais.toString(), mensaje, datasourcename);
      archivoBO.actualizarArchivoNotificado(idPais);

      JsonEnviarNotificacionPushDataResponse data = new JsonEnviarNotificacionPushDataResponse();
      data.setResultado(HTTPSTATUS_OK);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que obtiene la cantidad de notificaciones de archivos nuevos o modificados
   * @return cadena json que contiene la cantidad de archivos a notificar
   * @throws Exception 
   */  
  @RequestMapping(value = "/web/obtenerPorNotificar", method = RequestMethod.POST)
  @ResponseBody public String obtenerPorNotificar(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_POR_NOTIFICAR.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonObtenerPorNotificarRequest>>() {}.getType();
      JsonKioskoRequest<JsonObtenerPorNotificarRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();

      if (!validarTokenWeb(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long correlativoPais = request.getBody().getData().getPais();

      int cantidadNotificar = coleccionBO.obtenerPorNotificar(correlativoPais);

      JsonObtenerPorNotificarResponse data = new JsonObtenerPorNotificarResponse();
      data.setPorNotificar(cantidadNotificar);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que obtiene la ruta interna
   * @return cadena json que contiene la cantidad de archivos a notificar
   */  
  private String obtenerRutaInterna(String rutaExterna){
    if(StringUtils.isNotBlank(rutaExterna)){
      return rutaExterna.replaceAll(DOMINIO, DOMINIO_LECTURA);
    }
    return "";
  }

  @RequestMapping(value = "/movil/validarArchivoRol", method = RequestMethod.POST)
  @ResponseBody public String validarArchivoRol(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.VALIDAR_ARCHIVO_ROL.getNombre();
    try {
      Type tipo =
          new TypeToken<JsonKioskoRequest<JsonValidarArchivoRolDataRequest>>() {} .getType();
      JsonKioskoRequest<JsonValidarArchivoRolDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);
      String token = request.getBody().getData().getToken();
      String codigoRol = request.getBody().getData().getRol();
      Long idArchivo = request.getBody().getData().getIdArchivo();

      if (!validarTokenMovil(token)) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long codigoRespuesta = archivoBO.validarArchivoRol(idArchivo, codigoRol);

      if (codigoRespuesta == null) {
        codigoRespuesta = ERROR_VALIDAR_ROL;
      }

      JsonValidarArchivoRolDataResponse data = new JsonValidarArchivoRolDataResponse();
      data.setCodigoRespuesta(codigoRespuesta);
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, dispositivo);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }
}
