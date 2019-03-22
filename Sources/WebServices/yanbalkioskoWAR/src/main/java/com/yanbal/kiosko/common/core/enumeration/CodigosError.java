package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enum con los codigos de error de la aplicacion
 * 
 */
public enum CodigosError {
  EXITO_KIOSKO(0,"Exito"),
  ERROR_KIOSKO(1, "Error en el sistema Kiosko"), 
  ERROR_GENERACION_RESPUESTA_JSON(2,"Error Interno en el Servidor"), 
  SESION_INVALIDA(3, "Sesion Invalida"), 
  ERROR_SERVICIO_YANBAL(4, "Error en el servicio de Yanbal"), 
  ERROR_ROL_INEXISTENTE(5, "Rol inexistente"), 
  ERROR_PRIVILEGIOS(6, "Privilegios insuficientes"), 
  ERROR_NOMBRE_DUPLICADO(7,"Se ha ingresado un nombre duplicado"), 
  ERROR_DATOS_INSUFICIENTES(8,"Datos insuficientes para procesar el servicio");


  private String mensaje;
  private int codigo;

  /**
   * Constructor del Enum
   * 
   * @param codigo codigo de error
   * @param mensaje mensaje de error
   */
  CodigosError(int codigo, String mensaje) {
    this.mensaje = mensaje;
    this.codigo = codigo;
  }

  /**
   * Obtiene el mensaje de error
   * 
   * @return mensaje de error
   */
  public String getMensaje() {
    return mensaje;
  }

  /**
   * Obtiene el codigo de error
   * 
   * @return codigo de error
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Obtiene el codigo de error segun parametro
   * 
   * @param codigo de error buscado
   * @return codigo de error
   */
  public static CodigosError getCodigoError(int codigo) {
    for (CodigosError ce : CodigosError.values()) {
      if (ce.getCodigo() == codigo) {
        return ce;
      }
    }
    return null;
  }
}
