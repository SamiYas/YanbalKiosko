package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enum con los servicios de Yanbal
 * 
 */
public enum YanbalWebServices {
  LISTADO_PAISES(
      1,
      "http://proxynp.unique-yanbal.com/integraciondesa/WsIntegracionModularizacionWeb/rest/WSConsultasPaises/consPaisesMbl",
      "POST",
      "{\"IntegracionWSReq\":{\"Cabecera\":{\"UsuarioAplicacion\":\"13634\",\"CodigosPaisOD\":{\"CodigoPaisOD\":{\"Valor\":\"PER\"}},\"CodigoAplicacion\":\"PEDIDOSIOS\",\"CodigoPais\":\"PER\",\"CodigoInterfaz\":\"CPAISES\"},\"Detalle\":{\"Parametros\":{\"NombreCorto\":\"\",\"Estado\":\"1\",\"CodigoPais\":\"0\",\"Nombre\":\"\"}}}}"), DATOS_USUARIO(
      2,
      "http://proxynp.unique-yanbal.com/integraciondesa/WSIntegracionYanbalStoreWeb/rest/WSMantenimientoUsuarios/validaUsuarioMbl",
      "POST",
      "{\"IntegracionWSReq\":{\"Cabecera\":{\"CodigoInterfaz\":\"CVALUSR\",\"UsuarioAplicacion\":\"13634\",\"CodigoAplicacion\":\"KIOSKO\",\"CodigoPais\":\"_param1\",\"CodigosPaisOD\":{\"CodigoPaisOD\":{\"Valor\":\"_param1\"}}},\"Detalle\":{\"Parametros\":{\"TipoUsuario\":\"_param2\",\"Usuario\":\"_param3\",\"Password\":\"_param4\"}}}}");


  private int codigo;
  private String url;
  private String metodo;
  private String request;

  /**
   * Constructor del Enum
   * 
   * @param codigo codigo de servicio
   * @param mensaje url de servicio
   */
  YanbalWebServices(int codigo, String url, String metodo, String request) {
    this.codigo = codigo;
    this.url = url;
    this.metodo = metodo;
    this.request = request;
  }

  /**
   * Obtiene el request del servicio
   * 
   * @return request de servicio
   */
  public String getRequest() {
    return request;
  }

  /**
   * Obtiene el tipo de metodo
   * 
   * @return metodo de servicio
   */
  public String getMetodo() {
    return metodo;
  }

  /**
   * Obtiene el mensaje de error
   * 
   * @return url de servicio
   */
  public String getUrl() {
    return url;
  }

  /**
   * Obtiene el codigo de servicio
   * 
   * @return codigo de servicio
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Obtiene el codigo de servicio segun parametro
   * 
   * @param codigo de servicio buscado
   * @return codigo de servicio
   */
  public static YanbalWebServices getCodigoError(int codigo) {
    for (YanbalWebServices ce : YanbalWebServices.values()) {
      if (ce.getCodigo() == codigo) {
        return ce;
      }
    }
    return null;
  }
}
