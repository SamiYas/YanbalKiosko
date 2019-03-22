package yanbalkioskov2;

import java.util.List;

import junit.framework.TestCase;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yanbal.kiosko.bo.ColeccionBO;
import com.yanbal.kiosko.entities.Pais;

/**
 * Clase para las pruebas sobre la entidad Pais
 * @author alex.contreras
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring.xml" })
public class PaisTest extends TestCase {

	@Autowired
	SqlSessionFactory factory;

	@Autowired
	ColeccionBO coleccionBO;

	@Test
	@Ignore
	public void test() {
		SqlSession sesion = factory.openSession();
		List<Pais> listarTodo = sesion
				.selectList("com.yanbal.kiosko.dao.PaisDao.listarTodo");
		assertNotNull(listarTodo);
		sesion.close();
	}
}