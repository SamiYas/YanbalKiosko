package com.yanbal.kiosko.bo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.common.util.SeguridadUtil;
import com.yanbal.kiosko.dao.RolDao;
import com.yanbal.kiosko.dao.SesionWebDao;
import com.yanbal.kiosko.entities.Rol;
import com.yanbal.kiosko.entities.SesionWeb;
import com.yanbal.kiosko.exception.KioskoException;
import com.yanbal.kiosko.interfaces.rest.servicios.ClienteYanbal;
import com.yanbal.lib.corp.interfaces.EncryptorAES;
import com.yanbal.lib.corp.interfaces.IEncryptor;

/**
 * Servicio para la entidad Sesion Web
 * 
 * @author lennin.davila
 * 
 */
@Service
public class SesionWebBO {

  @Autowired
  private SesionWebDao sesionWebDao;

  @Autowired
  private RolDao rolDao;

  private IEncryptor encrypter = EncryptorAES.getInstance();

  /**
   * Metodo de negocio que registra en la base de datos la sesion que se inicio en el administrador
   * web.
   * 
   * @param objSesionWeb un objecto entidad de la sesion web.
   * @return SesionWeb retorna un objeto sesion que contiene los atributos generados por el inicio
   *         de sesion.
   * @see retorna un tipo de dato SesionWeb.
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public SesionWeb registrarSesion(SesionWeb objSesionWeb, String codigoRol) throws KioskoException {
    try {
      // verificar rol existente
      Rol rol = rolDao.obtenerPorCodigo(codigoRol);
      if (rol != null && rol.getCorrelativoRol() != null) {
        StringBuilder sb = new StringBuilder();
        sb.append(objSesionWeb.getNombreUsuario());
        sb.append(objSesionWeb.getClave());
        sb.append(objSesionWeb.getCorrelativoPais());
        sb.append(System.currentTimeMillis());
        String tokenText = sb.toString();
        tokenText = SeguridadUtil.cadenaMD5(tokenText);
        objSesionWeb.setNombreUsuario(encrypter.encrypt(objSesionWeb.getNombreUsuario()));
        objSesionWeb.setClave(encrypter.encrypt(objSesionWeb.getClave()));
        objSesionWeb.setToken(tokenText);
        objSesionWeb.setCorrelativoRol(rol.getCorrelativoRol());

        sesionWebDao.eliminarSesionesExpiradas(objSesionWeb.getNombreUsuario());
        sesionWebDao.insertar(objSesionWeb);
        // obtener correlativo rol

        objSesionWeb.setCorrelativoRol(rol.getCorrelativoRol());
      } else {
        objSesionWeb.setCorrelativoRol(null);
        return objSesionWeb;
      }
    } catch (Exception ex) {
      throw new KioskoException(ex.getMessage(), ex);
    }
    return objSesionWeb;
  }

  /**
   * Metodo que cierra la sesion web
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public void cerrarSesion(String token) {
    sesionWebDao.eliminarPorToken(token);
  }

  /**
   * Metodo que valida
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public SesionWeb validarToken(String token) {
    sesionWebDao.eliminarSiTokenHaExpirado(token);
    return sesionWebDao.obtenerPorToken(token);
  }

  /**
   * Metodo que elimina una coleccion
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   * @throws UnsupportedEncodingException
   * @throws InvalidAlgorithmParameterException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public SesionWeb obtenerPorToken(String token) throws InvalidKeyException,
      NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
      BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
    SesionWeb sesionWeb = sesionWebDao.obtenerPorToken(token);
    if (sesionWeb != null && sesionWeb.getNombreUsuario() != null
        && !("").equals(sesionWeb.getNombreUsuario())) {
      sesionWeb.setNombreUsuario(encrypter.decrypt(sesionWeb.getNombreUsuario()));
    }
    if (sesionWeb != null && sesionWeb.getClave() != null && !("").equals(sesionWeb.getClave())) {
      sesionWeb.setClave(encrypter.decrypt(sesionWeb.getClave()));
    }
    return sesionWeb;
  }

  /**
   * Metodo que elimina una coleccion
   * 
   * @param token cadena unica que identifica la sesion de un dispositivo
   * @throws IOException
   * @throws HttpException
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public String obtenerDatosUsuario(String objJson) throws HttpException, IOException {
    return ClienteYanbal.obtenerDatosUsuario(objJson);
  }

}
