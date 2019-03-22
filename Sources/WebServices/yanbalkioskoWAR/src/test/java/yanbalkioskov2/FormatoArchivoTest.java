package yanbalkioskov2;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yanbal.kiosko.bo.FormatoArchivoBO;
import com.yanbal.kiosko.entities.FormatoArchivo;
import com.yanbal.kiosko.exception.KioskoException;

/**
 * Clase para las pruebas sobre la entidad Formato Archivo
 * @author alex.contreras
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring.xml",
		"classpath:spring/spring-dispatcher-servlet.xml" })
public class FormatoArchivoTest {

	@Autowired
	FormatoArchivoBO formatoArchivoBO;

	@Test
	@Ignore
	public void pruebaInsertaColeccion() {
		FormatoArchivo s;
		try {
			s = formatoArchivoBO.obtenerUmbral(6L);
			Assert.assertNotNull(s);
		} catch (KioskoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void prueba() {
		List<FormatoArchivo> lista;
		lista = null;//formatoArchivoBO.listarUmbrales();
		Assert.assertNotNull(lista);
	}

}
