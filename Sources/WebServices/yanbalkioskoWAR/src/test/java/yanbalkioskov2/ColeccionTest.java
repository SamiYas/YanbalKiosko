package yanbalkioskov2;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yanbal.kiosko.bo.ColeccionBO;
import com.yanbal.kiosko.entities.Coleccion;

/**
 * Clase para las pruebas sobre la entidad Coleccion
 * @author alex.contreras
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring.xml",
		"classpath:spring/spring-dispatcher-servlet.xml" })
public class ColeccionTest {

	@Autowired
	ColeccionBO coleccionBO;

	@Test
	@Ignore
	public void pruebaInsertaColeccion() {
		Coleccion coleccion = new Coleccion(13L, 1L, 3L,
				"Coleccion de prueba TT3", 1, 2, "Prueba T 3", "redrojo",
				new Date());

		Long respuesta = coleccionBO.insertarColeccion(coleccion);

		Assert.assertNotNull(respuesta);

	}

	@Test
	@Ignore
	public void testObtenerColeccionesPorColeccionPadre() {
		//List<Coleccion> lista = coleccionBO.listarColecciones(13L);
		//Assert.assertNotNull(lista);
	}

}
