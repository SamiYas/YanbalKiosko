package com.yanbal.kiosko.common.core;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yanbal.kiosko.bo.DispositivoActivoBO;
import com.yanbal.kiosko.bo.SesionWebBO;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.core.enumeration.TipoEntidad;
import com.yanbal.kiosko.common.core.enumeration.TipoUsuario;
import com.yanbal.kiosko.common.json.response.JsonKioskoBodyResponse;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;
import com.yanbal.kiosko.common.json.response.JsonKioskoResponse;
import com.yanbal.kiosko.common.json.util.HeaderUtil;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.dao.RolDao;
import com.yanbal.kiosko.entities.DispositivoActivo;
import com.yanbal.kiosko.entities.SesionWeb;

/**
 * Clase abstracta controladora.
 * 
 * @author lennin.davila
 */
@Controller
public abstract class KioskoControler {

  protected SesionWeb sesion;
  protected DispositivoActivo dispositivo;

  @Autowired
  private SesionWebBO sesionWebBo;

  @Autowired
  private DispositivoActivoBO dispositivoActivoBO;

  @Autowired
  private RolDao rolDao;

  protected static final String HTTPSTATUS_OK = "HttpStatus.OK";
  protected static final String HTTPSTATUS_ERROR = "HttpStatus.ERROR";
  /**
   * Metodo que devuelve uno String del response a devolver incluyendo el nombre del servicio del
   * cual provee
   * 
   * @param data objeto contenido del response
   * @param nombreServicio nombre de servicio usado
   * @return un String con el contenido parseado
   */
  protected String getStringResponse(JsonKioskoDataResponse data, String nombreServicio) {
    JsonKioskoResponse response = new JsonKioskoResponse();
    JsonKioskoBodyResponse body = new JsonKioskoBodyResponse();
    body.setData(data);
    response.setHeader(HeaderUtil.getHeader(nombreServicio));
    response.setBody(body);
    return JsonUtil.convertirAJson(response);
  }

  /**
   * Metodo que devuelve un String del response con codigo de error incluyendo el nombre del
   * servicio del cual provee
   * 
   * @param codigo codigo de error
   * @param nombreServicio el nombre del servicio utilizado
   * @return response a parsear
   */
  protected String getStringResponse(CodigosError codigo, String nombreServicio) {
    JsonKioskoResponse response = new JsonKioskoResponse();
    JsonKioskoBodyResponse body = new JsonKioskoBodyResponse();
    response.setHeader(HeaderUtil.getHeader(codigo, nombreServicio));
    response.setBody(body);
    return JsonUtil.convertirAJson(response);
  }

  /**
   * Metodo que devuelve un String del response con codigo de error incluyendo el nombre del
   * servicio del cual provee y data de error
   * 
   * @param codigo codigo de error
   * @param nombreServicio el nombre del servicio utilizado
   * @return response a parsear
   */
  protected String getStringResponse(CodigosError codigo, JsonKioskoDataResponse data,
      String nombreServicio) {
    JsonKioskoResponse response = new JsonKioskoResponse();
    JsonKioskoBodyResponse body = new JsonKioskoBodyResponse();
    body.setData(data);
    response.setHeader(HeaderUtil.getHeader(codigo, nombreServicio));
    response.setBody(body);
    return JsonUtil.convertirAJson(response);
  }

  /**
   * Metodo que valida el token de sesion web
   * @param token de la sesion.
   * @return valor booleano que indica so el token es valido
   */  
  protected boolean validarTokenWeb(String token) {
    if (StringUtils.isBlank(token)) {
      return false;
    }
    sesion = sesionWebBo.validarToken(token);
    return sesion != null && StringUtils.isNotBlank(sesion.getNombreUsuario());
  }

  /**
   * Metodo que valida el token de movil
   * @param token de la sesion.
   * @return valor booleano que indica so el token es valido
   */   
  protected boolean validarTokenMovil(String token) {
    if (StringUtils.isBlank(token)) {
      return false;
    }
    dispositivo = dispositivoActivoBO.validarToken(token);
    return dispositivo != null && StringUtils.isNotBlank(dispositivo.getNombreUsuario());
  }

  /**
   * Metodo que retorna el response de sesion invalida
   * @param nombreServicio de la sesion.
   * @return valor cadena con el response de respuesta
   */   
  protected String getResponseSesionInvalida(String nombreServicio) {
    JsonKioskoResponse response = new JsonKioskoResponse();
    JsonKioskoBodyResponse body = new JsonKioskoBodyResponse();
    response.setHeader(HeaderUtil.getHeader(CodigosError.SESION_INVALIDA, nombreServicio));
    response.setBody(body);
    return JsonUtil.convertirAJson(response);
  }

  /**
   * Metodo que valida los privilegios de los roles Corp y UN
   * @param correlativoEntidad correlativo de la entidad
   * @param tipoEntidad tipo de entidad
   * @return valor booleano que determina si se cuenta con privilegios corporativos
   */   
  protected boolean validarPrivilegios(Long correlativoEntidad, TipoEntidad tipoEntidad) {
    if (esUsuarioCorp()) {
      return true;
    }
    Long rolEntidad = rolDao.obtenerRol(correlativoEntidad, tipoEntidad.getValor());
    return validarRol(rolEntidad);
  }

  /**
   * Metodo que indica si el usuario es de rol corporativo.
   * @return valor booleano que determina si se cuenta con privilegios corporativos
   */ 
  protected boolean esUsuarioCorp() {
    return TipoUsuario.CORP.getCorrelativoRol() == sesion.getCorrelativoRol();
  }

  /**
   * Metodo que indica si el rol es valido
   * @param rol: correlativo del rol
   * @return valor booleano que determina si es un rol valido.
   */   
  protected boolean validarRol(Long rol) {
    return rol == null || sesion.getCorrelativoRol() == rol;
  }

}
