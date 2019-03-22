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
import com.yanbal.kiosko.bo.RolBO;
import com.yanbal.kiosko.common.core.KioskoControler;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.core.enumeration.Servicio;
import com.yanbal.kiosko.common.json.JsonListarRolesDataRequest;
import com.yanbal.kiosko.common.json.JsonListarRolesDataResponse;
import com.yanbal.kiosko.common.json.model.JsonRol;
import com.yanbal.kiosko.common.json.request.JsonKioskoRequest;
import com.yanbal.kiosko.common.json.util.JsonUtil;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.Rol;

/**
 * Clase controladora de la entidad Rol
 * 
 * @author lennin.davila
 * 
 */
@Controller
public class WsGestionRolControler extends KioskoControler {

  @Autowired
  private RolBO rolBo;

  /**
   * Devuelve una cadena json con un arreglo de roles
   * 
   * @return cadena json con los roles
   * @throws Exception 
   */
  @RequestMapping(value = "/web/listarRoles", method = RequestMethod.POST)
  @ResponseBody public String listarRoles(@RequestBody String objJson) throws Exception {
    final String nombreServicio = Servicio.LISTAR_ROLES.getNombre();
    try {
      Type tipo = new TypeToken<JsonKioskoRequest<JsonListarRolesDataRequest>>() {}.getType();
      JsonKioskoRequest<JsonListarRolesDataRequest> request =
          JsonUtil.convertirDeJson(objJson, tipo);

      if (!validarTokenWeb(request.getBody().getData().getToken())) {
        return getResponseSesionInvalida(nombreServicio);
      }

      List<Rol> lista = rolBo.listarRoles();
      JsonListarRolesDataResponse data = new JsonListarRolesDataResponse();
      data.setRoles(obtenerJsonRoles(lista));
      return getStringResponse(data, nombreServicio);
    } catch (Exception ex) {
      EventoLogBuilder.obtenerEvento(this.getClass(), ex, sesion);
      return getStringResponse(CodigosError.ERROR_KIOSKO, nombreServicio);
    }
  }

  /**
   * Metodo que retorna un objeto lista rol para parsear a json desde un lista de roles
   * @param lista: lista de roles
   * @return objeto lista con los roles a parsear
   */   
  private List<JsonRol> obtenerJsonRoles(List<Rol> lista) {
    List<JsonRol> listaRoles = new ArrayList<JsonRol>();
    if (lista != null && !lista.isEmpty()) {
      for (Rol rol : lista) {
        JsonRol json = new JsonRol();
        json.setCodigo(rol.getCodigoRol());
        json.setId(rol.getCorrelativoRol());
        json.setNombre(rol.getNombre());
        listaRoles.add(json);
      }
    }
    return listaRoles;
  }
}
