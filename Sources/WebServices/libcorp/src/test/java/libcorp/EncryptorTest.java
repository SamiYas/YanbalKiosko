package libcorp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.yanbal.lib.corp.seguridad.encriptacion.EncryptorAES;
import com.yanbal.lib.corp.seguridad.encriptacion.IEncryptor;

public class EncryptorTest {

	private static final String ALGORITMO_DEFAULT = "AES/CBC/PKCS7Padding";
	private static final String KEY_DEFAULT_VALUE = "0123456789abcdef";
	private static final String ITERACIONES_DEFAULT = "1";
	private static final String POOL_SIZE_DEFAULT = "1";
	private static final String SALT_DEFAULT = "fedcba9876543210";
	private static final String SALT_SIZE_DEFAULT = "16";
	private static final String OUTPUT_TYPE_DEFAULT = "hex";

	private String message;
	private Map<String, Object> parametros;
	private IEncryptor encrypter = null;//EncryptorAES.getInstance();

	@Before
	public void setUp() {
		message = "Este en un mensaje a encriptar.";
		parametros = new HashMap<String, Object>();
		parametros.put(EncryptorAES.PARAMETRO_ALGORITMO, ALGORITMO_DEFAULT);
		parametros.put(EncryptorAES.PARAMETRO_LLAVE, KEY_DEFAULT_VALUE);
//		parametros
//				.put(EncryptorAES.PARAMETRO_NRO_ITERACIONES, ITERACIONES_DEFAULT);
//		parametros.put(EncryptorAES.PARAMETRO_TAMANIO_POOL, POOL_SIZE_DEFAULT);
		parametros.put(EncryptorAES.PARAMETRO_SALT, SALT_DEFAULT);
		// parametros.put(EncryptorAES.PARAMETRO_TAMANIO_SALT, SALT_SIZE_DEFAULT);
		parametros.put(EncryptorAES.PARAMETRO_TIPO_SALIDA, OUTPUT_TYPE_DEFAULT);
	}

	@Test
	@Ignore
	public void testEncriptar() throws Exception {

		System.out.println("ORIGINAL: " + message);
		assertNotNull(encrypter);

		String encriptado = encrypter.encrypt(message);
		System.out.println("ENCRIPTADO: " + encriptado);

		assertNotNull(encriptado);
		assertNotEquals(encriptado, "");

		String desencriptado = encrypter.decrypt(encriptado);
		System.out.println("DESENCRIPTADO: " + desencriptado + "\n");

		assertNotNull(desencriptado);
		assertNotEquals(desencriptado, "");

		assertEquals(message, desencriptado);
	}

	@Test
	@Ignore
	public void testEncriptarConParametros() throws Exception {

		System.out.println("ORIGINAL: " + message);
		assertNotNull(encrypter);

		String encriptado = encrypter.encrypt(message, parametros);
		System.out.println("ENCRIPTADO: " + encriptado);

		assertNotNull(encriptado);
		assertNotEquals(encriptado, "");

		String desencriptado = encrypter.decrypt(encriptado, parametros);
		System.out.println("DESENCRIPTADO: " + desencriptado + "\n");
		
		assertNotNull(desencriptado);
		assertNotEquals(desencriptado, "");

		assertEquals(message, desencriptado);
	}
	
	@Test
	public void testVarios() throws Exception {
		
		String[] mensajes = {"soldier", "helloworld", "Mensaje a encriptar", "Test contraseña ingresada", "123456789", "password1", "blablabla el quijorte de la mancha ñandú"  };
		
		for(int i = 0; i < mensajes.length; i++) {
			message = mensajes[i];
			testEncriptarConParametros();
		}
		
		System.out.println("************************************************");
		System.out.println("*     ENCRIPTACION DE PROPERTIES (base64)      *");
		System.out.println("************************************************\n");
		
		for(int i = 0; i < mensajes.length; i++) {
			message = mensajes[i];
			testEncriptar();
		}
		
	}

}
