package com.yanbal.kiosko.bo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.common.util.SeguridadUtil;
import com.yanbal.kiosko.dao.DispositivoActivoDao;
import com.yanbal.kiosko.entities.DispositivoActivo;

/**
 * Servicio para la entidad Dispositivo Activo
 * 
 * @author lennin.davila
 *
 */
@Service
public class DispositivoActivoBO {

  @Autowired
  private DispositivoActivoDao dispositivoActivoDao;

  /**
   * Metodo de negocio que registra la sesion iniciada en los moviles en la base de datos del
   * administrador.
   * 
   * @param objDispositivoActivo un objecto entidad que contiene los atributos del dispositivo con
   *        la sesion activa.
   * @return DispositivoActivo retorna un objeto con los atributos que se generaron durante el
   *         inicio de sesion.
   * @see retorna un tipo de dato DispositivoActivo.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public DispositivoActivo registrarSesion(DispositivoActivo objDispositivoActivo) {
      StringBuilder sb = new StringBuilder();
      sb.append(objDispositivoActivo.getNombreUsuario());
      sb.append(objDispositivoActivo.getClave());
      sb.append(objDispositivoActivo.getCodigoPais());
      sb.append(System.currentTimeMillis());
      String tokenText = sb.toString();
      tokenText = SeguridadUtil.cadenaMD5(tokenText);
      objDispositivoActivo.setToken(tokenText);
      objDispositivoActivo.setFechaUltimaSinc(new Date());
      dispositivoActivoDao.registrarSesion(objDispositivoActivo);
      return objDispositivoActivo;
  }

  /**
   * Metodo que elimina una coleccion
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public void cerrarSesion(String token) {
    dispositivoActivoDao.eliminarPorToken(token);
  }

  /**
   * Metodo que valida el token de dispositivo activo
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public DispositivoActivo validarToken(String token) {
    return dispositivoActivoDao.obtenerPorToken(token);
  }

  /**
   * Metodo que obtiene todos los tokens de los dispositivos para el envio de notificaciones Push
   * 
   * @param correlativoPais : correlativo de pais, indica que traera dispositivos del pais
   *        especificado
   * @param diasInactividad : numero que indica los dias de inactividad minimo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<DispositivoActivo> obtenerDispositivos(Long correlativoPais, int diasInactividad) {
    return dispositivoActivoDao.obtenerDispositivos(correlativoPais, diasInactividad);
  }
}
