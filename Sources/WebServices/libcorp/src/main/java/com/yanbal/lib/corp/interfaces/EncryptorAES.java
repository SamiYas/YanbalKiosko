package com.yanbal.lib.corp.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

/**
 * Clase de Encriptacion AES
 * 
 * @author lennin.davila
 *
 */
public class EncryptorAES implements IEncryptor {

  private static final Logger LOGGER = LogManager.getLogger(EncryptorAES.class);
  // Map keys
  public static final String PARAMETRO_ALGORITMO = "KEY_ALGORITMO";
  public static final String PARAMETRO_LLAVE = "KEY_LLAVE";
  public static final String PARAMETRO_SALT = "KEY_SALT";
  public static final String PARAMETRO_TIPO_SALIDA = "KEY_TIPO_SALIDA";

  // properties name
  private static final String ENCRYPTION_PROPERTIES_FILE =
      "encriptacion/encryptionParameters.properties";
  // properties keys
  private static final String PROPERTIES_ALGORITMO = "encriptacion.algoritmo";
  private static final String PROPERTIES_LLAVE = "encriptacion.llave";
  private static final String PROPERTIES_SALT = "encriptacion.salt";
  private static final String PROPERTIES_TIPO_SALIDA = "encriptacion.tipo_de_salida";

  public static final String HEX_ENCODE = "hex";
  public static final String BASE64_ENCODE = "base64";

  private static IEncryptor instance;
  private Map<String, Object> parametrosProperties = new HashMap<String, Object>();

  public static IEncryptor getInstance() {
    if (instance == null) {
      instance = new EncryptorAES();
    }
    return instance;
  }

  /**
   * Constructor de clase
   * 
   * @throws IOException
   */
  private EncryptorAES(){
	    // cargar properties
	    Properties properties = new Properties();
	    // Se pone en bloque para que el GC no considere la variable
	    // 'propertiesFile'
	    // luego de ser usada (por scope de la variable en un bloque).
	    // Esta variable es algo pesada.
	    {
	      // Se busca el archivo en los archivos de recursos
	      // de la aplicacion.
	      final InputStream propertiesFile =
	          Thread.currentThread().getContextClassLoader()
	              .getResourceAsStream(ENCRYPTION_PROPERTIES_FILE);
	      try {
	        properties.load(propertiesFile);
	        propertiesFile.close();
	      } catch (final IOException e) {
	    	  LOGGER.error(e.getMessage(),e);
	      }
	    }

	    parametrosProperties.put(PARAMETRO_ALGORITMO, properties.get(PROPERTIES_ALGORITMO));
	    parametrosProperties.put(PARAMETRO_LLAVE, properties.get(PROPERTIES_LLAVE));
	    parametrosProperties.put(PARAMETRO_SALT, properties.get(PROPERTIES_SALT));
	    parametrosProperties.put(PARAMETRO_TIPO_SALIDA, properties.get(PROPERTIES_TIPO_SALIDA));
	  }


  /**
   * Metodo que encripta una cadena recibida
   */
  @Override
  public String encrypt(String message) throws Exception {
    return encrypt(message, parametrosProperties);
  }

  /**
   * Metodo que encripta una cadena recibida segun los parametros recibidos
   */
  @Override
  public String encrypt(String message, Map<String, Object> parametros) throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    final IvParameterSpec ivspec =
        new IvParameterSpec(((String) parametros.get(PARAMETRO_SALT)).getBytes());
    final SecretKeySpec keyspec =
        new SecretKeySpec(((String) parametros.get(PARAMETRO_LLAVE)).getBytes(),
            (String) parametros.get(PARAMETRO_ALGORITMO));

    Cipher c = Cipher.getInstance((String) parametros.get(PARAMETRO_ALGORITMO));
    c.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
    byte[] encVal = c.doFinal(padString(message).getBytes("UTF-8"));
    String encryptedValue = "";
    if (HEX_ENCODE.equals((String) parametros.get(PARAMETRO_TIPO_SALIDA))) {
      encryptedValue = Hex.toHexString(encVal);
    } else {
      encryptedValue = Base64.toBase64String(encVal);
    }
    return encryptedValue;
  }

  /**
   * Metodo que desencripta una cadena recibida
   * 
   * @throws UnsupportedEncodingException
   * @throws InvalidAlgorithmParameterException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  @Override
  public String decrypt(String message) throws InvalidKeyException, NoSuchAlgorithmException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
      InvalidAlgorithmParameterException, UnsupportedEncodingException {
    return decrypt(message, parametrosProperties);
  }

  /**
   * Metodo que desencripta una cadena recibida segun los parametros recibidos
   * 
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeyException
   * @throws UnsupportedEncodingException
   */
  @Override
  public String decrypt(String message, Map<String, Object> parametros)
      throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
      BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
      UnsupportedEncodingException {
    Security.addProvider(new BouncyCastleProvider());
    final IvParameterSpec ivspec =
        new IvParameterSpec(((String) parametros.get(PARAMETRO_SALT)).getBytes());
    final SecretKeySpec keyspec =
        new SecretKeySpec(((String) parametros.get(PARAMETRO_LLAVE)).getBytes(),
            (String) parametros.get(PARAMETRO_ALGORITMO));
    Cipher c = Cipher.getInstance((String) parametros.get(PARAMETRO_ALGORITMO));
    c.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

    byte[] decordedValue = null;

    if (HEX_ENCODE.equals((String) parametros.get(PARAMETRO_TIPO_SALIDA))) {
      decordedValue = Hex.decode(message);
    } else {
      decordedValue = Base64.decode(message);
    }

    byte[] decValue = c.doFinal(decordedValue);
    String decryptedValue = new String(decValue, "UTF-8");
    return decryptedValue.trim();
  }

  /**
   * Metodo que retorna una cadena
   */
  private String padString(String source) {
    char paddingChar = ' ';
    int size = 16;
    int x = source.length() % size;
    int padLength = size - x;
    StringBuilder sb = new StringBuilder(source);
    for (int i = 0; i < padLength; i++) {
      sb.append(paddingChar);
    }
    return sb.toString();
  }

}
