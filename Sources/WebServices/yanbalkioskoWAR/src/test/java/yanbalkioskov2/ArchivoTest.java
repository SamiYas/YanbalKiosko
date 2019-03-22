package yanbalkioskov2;

import org.junit.Test;

import com.yanbal.kiosko.common.util.ArchivoUtil;

public class ArchivoTest {

	@Test
	public void probarArchivoUtil() throws Exception{
		String nombreOriginal = "nombreArchiv.jpg";
		String nombreRutaOriginal = "F:/yanbal_files/nombreArchiv.jpg";
		
		System.out.println(ArchivoUtil.obtenerExtension(nombreOriginal));
		System.out.println(ArchivoUtil.obtenerNombre(nombreOriginal));
		System.out.println(ArchivoUtil.obtenerNombreUnico(nombreOriginal));
		
		System.out.println(ArchivoUtil.obtenerNombreCompletoDeRuta(nombreRutaOriginal));
	}
	
}
