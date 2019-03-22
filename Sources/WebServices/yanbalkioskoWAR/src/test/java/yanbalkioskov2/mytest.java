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

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring-mybatis.xml","classpath:spring/spring.xml" })

public class mytest extends TestCase {

	 //@Autowired
	 //SqlSessionFactory factory;
	
	 @Test(expected = Exception.class)
	 public void test() {
			//PushAPNS push;
			try {
				//new ClientePush();
				String ws = "";// YanbalRest.obtenerPaisesYanbal();
				if(ws.equals("")){
					System.out.println("es vacio");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		 
		  /*SqlSession sesion = factory.openSession();
		  List<FormatoArchivo> listar = sesion.selectList("com.yanbal.kiosko.dao.FormatoArchivoDao.listarExtensionTamanho");
		  assertNotNull(listar);
		  System.out.println(listar.size());
		  sesion.close();*/	
	 }

}