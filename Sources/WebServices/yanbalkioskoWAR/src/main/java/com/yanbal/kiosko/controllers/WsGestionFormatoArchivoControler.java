package com.yanbal.kiosko.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.yanbal.kiosko.bo.FormatoArchivoBO;
import com.yanbal.kiosko.common.core.KioskoControler;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.core.enumeration.Servicio;
import com.yanbal.kiosko.common.json.JsonActualizarUmbralDataRequest;
import com.yanbal.kiosko.common.json.JsonActualizarUmbralDataResponse;
import com.yanbal.kiosko.common.json.JsonListarUmbralesDataRequest;
import com.yanbal.kiosko.common.json.JsonListarUmbralesDataResponse;
import com.yanbal.kiosko.common.json.JsonObtenerUmbralDataRequest;
import com.yanbal.kiosko.common.json.JsonObtenerUmbralDataResponse;
import com.yanbal.kiosko.common.json.model.JsonUmbral;
import com.yanbal.kiosko.common.json.request.JsonKioskoRequest;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.FormatoArchivo;

/**
 * Clase controladora de umbrales
 * 
 * @author alex.contreras
 * 
 */
@Controller
public class WsGestionFormatoArchivoControler extends KioskoControler {

  @Autowired
  private FormatoArchivoBO formatoArchivoBO;

  /**
   * Devuelve una cadena json con un arreglo de umbrales
   * 
   * @return cadena json con los umbrales
   * @throws Exception 
   */
  @RequestMapping(value = "/web/listarUmbrales", method = RequestMethod.POST)
  @ResponseBody public String listarUmbrales(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.LISTAR_UMBRALES.getNombre();
    try {
      JsonListarUmbralesDataResponse data = new JsonListarUmbralesDataResponse();
      Type tipo = new TypeToken<JsonKioskoRequest<JsonListarUmbralesDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonListarUmbralesDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long numeroCorrelativo = request.getBody().getData().getPais();

      List<FormatoArchivo> formatosArchivos = formatoArchivoBO.listarUmbrales(numeroCorrelativo);

      List<JsonUmbral> umbrales = new ArrayList<JsonUmbral>();
      for (FormatoArchivo formato : formatosArchivos) {
        JsonUmbral umbral = new JsonUmbral();
        umbral.setCarga(formato.getCarga());
        umbral.setDescarga(formato.getDescarga());
        umbral.setDescripcion(formato.getDescripcion());
        umbral.setExtension(formato.getExtension());
        umbral.setId(formato.getCorrelativoFormatoArchivo());
        umbral.setRol(formato.getCorrelativoRol());
        umbrales.add(umbral);
      }

      data.setUmbrales(umbrales);
      data.setTotalRegistros(formatosArchivos.size());
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Devuelve una cadena json con un umbral
   * 
   * @return cadena json con el umbral obtenido
   * @throws Exception 
   */
  @RequestMapping(value = "/web/obtenerUmbral", method = RequestMethod.POST)
  @ResponseBody public String obtenerUmbral(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.OBTENER_UMBRAL.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonObtenerUmbralDataRequest>>() {}.getType();

      JsonKioskoRequest<JsonObtenerUmbralDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      JsonObtenerUmbralDataResponse data = new JsonObtenerUmbralDataResponse();

      Long numeroCorrelativo = request.getBody().getData().getId();

      FormatoArchivo formato = formatoArchivoBO.obtenerUmbral(numeroCorrelativo);

      if (formato != null) {

        data.setCarga(formato.getCarga());
        data.setDescarga(formato.getDescarga());
        data.setDescripcion(formato.getDescripcion());
        data.setExtension(formato.getExtension());
        data.setRol(formato.getCorrelativoRol());
        data.setId(formato.getCorrelativoFormatoArchivo());
      }

      return getStringResponse(data, nombreServicio);

    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Actualiza un umbral segun los datos especificados
   * 
   * @return cadena json
   * @throws Exception 
   */
  @RequestMapping(value = "/web/actualizarUmbral", method = RequestMethod.POST)
  @ResponseBody public String actualizarUmbral(@RequestBody String objJson) {
    final String nombreServicio = Servicio.ACTUALIZAR_UMBRAL.getNombre();
    try {

      Type tipo = new TypeToken<JsonKioskoRequest<JsonActualizarUmbralDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonActualizarUmbralDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      Long id = request.getBody().getData().getId();
      Long rol = sesion.getCorrelativoRol();
      String extension = request.getBody().getData().getExtension();
      String descripcion = request.getBody().getData().getDescripcion();
      Double carga = request.getBody().getData().getCarga();
      Double descarga = request.getBody().getData().getDescarga();

      // validar privilegios (solo CORP)
      if (!esUsuarioCorp()) {
        return getStringResponse(CodigosError.ERROR_PRIVILEGIOS, nombreServicio);
      }

      FormatoArchivo formatoArchivo =
          new FormatoArchivo(id, rol, extension, descripcion, descarga, carga, sesion.getNombreUsuario());

      Long filas = formatoArchivoBO.actualizarUmbral(formatoArchivo);
      String codigoRespuesta = (filas != null) ? HTTPSTATUS_OK : HTTPSTATUS_ERROR;

      JsonActualizarUmbralDataResponse data = new JsonActualizarUmbralDataResponse();
      data.setResultado(codigoRespuesta);

      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

}
