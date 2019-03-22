package com.yanbal.lib.corp.interfaces;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Interface de Encriptacion
 * 
 * @author lennin.davila
 *
 */
public interface IEncryptor {

  /**
   * Firma de metodo de encriptacion segun cadena
   */
  public String encrypt(String message) throws Exception;

  /**
   * Firma de metodo de encriptacion segun cadena y parametro
   */
  public String encrypt(String message, Map<String, Object> parametros) throws Exception;

  /**
   * Firma de metodo de desencriptacion segun cadena
   */
  public String decrypt(String message) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException;

  /**
   * Firma de metodo de desencriptacion segun cadena y parametros
   */
  public String decrypt(String message, Map<String, Object> parametros) throws Exception;

}
