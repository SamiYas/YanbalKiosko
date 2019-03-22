package yanbalkioskov2;

import java.util.Date;

import org.junit.Test;

import com.yanbal.kiosko.common.util.FechaUtil;

public class FechaTest {

	@Test
	public void imprimirFormatoFecha() {
		Date fechaActual = new Date();
		String fechaFormateada =  FechaUtil.getFechaFormatoFecha(fechaActual);
		System.out.println(fechaFormateada);
	}
	
	@Test
	public void imprimirFormatoFechaYHora() {
		Date fechaActual = new Date();
		String fechaFormateada =  FechaUtil.getFechaFormatoFechaYHora(fechaActual);
		System.out.println(fechaFormateada);
	}

	@Test
	public void imprimirFormatoFechaHoraYMillis() {
		Date fechaActual = new Date();
		String fechaFormateada =  FechaUtil.getFechaFormatoFechaHoraYMillis(fechaActual);
		System.out.println(fechaFormateada);
	}

	@Test
	public void verDateDesdeFormatoFecha() throws Exception {
		Date fechaActual = FechaUtil.getFechaFormatoFecha("24/06/2015");
		Date fechaHoraActual = FechaUtil.getFechaFormatoFechaYHora("24/06/2015 17:51:38");
		Date fechaHoraMillisActual = FechaUtil.getFechaFormatoFechaYHora("24/06/2015 17:51:38:555");
	}
	
}
