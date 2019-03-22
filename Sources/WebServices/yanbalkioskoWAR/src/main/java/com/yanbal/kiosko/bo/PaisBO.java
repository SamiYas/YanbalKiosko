package com.yanbal.kiosko.bo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.common.util.FechaUtil;
import com.yanbal.kiosko.dao.PaisDao;
import com.yanbal.kiosko.entities.Pais;
import com.yanbal.kiosko.entities.Parametro;
import com.yanbal.kiosko.interfaces.rest.servicios.ClienteYanbal;

/**
 * Clase de negocio de Pais, en esta clase se implementa los metodos para la gestion de los paises.
 * 
 */
@Service
public class PaisBO {

  @Autowired
  private PaisDao paisDao;

  @Autowired
  private ParametroBO parametroBO;

  /**
   * Metodo de negocio que obtiene todos los paises de la base de datos.
   * 
   * @return retorna un objecto de lista, contiene todos los paises desde la base de datos.
   * @see List<Pais> objeto tipo lista.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Pais> listarPaises() {
    return paisDao.listarTodo();
  }

  /**
   * Metodo de negocio que obtiene un pais segun el correlativo enviado.
   * 
   * @param correlativo el numero de correlativo por el cual se filtrara en la base de datos.
   * @return objPais un objeto pais, es el objeto devuelto desde la base de datos.
   * @see retorna un tipo de objeto Pais.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public Pais obtenerPais(Long correlativo) {
    return paisDao.obtenerPorCorrelativo(correlativo);
  }

  /**
   * Metodo de negocio que obtiene un pais segun el correlativo enviado.
   * 
   * @param correlativo el numero de correlativo por el cual se filtrara en la base de datos.
   * @return objPais un objeto pais, es el objeto devuelto desde la base de datos.
   * @throws IOException 
   * @throws HttpException 
   * @see retorna un tipo de objeto Pais.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
      isolation = Isolation.READ_COMMITTED)
  public void actualizarPais() throws HttpException, IOException {
    Parametro parametro = parametroBO.obtenerFechaUltimaActualizacion("FechaUltimaActualizacionPais");
    Date fechaUltima = FechaUtil.getFechaFormatoFecha(parametro.getValor());
    Date fechaActual = FechaUtil.getFechaActual();

    List<Pais> lstpaises = null;
    if (fechaActual.after(fechaUltima)) {
      lstpaises = ClienteYanbal.obtenerPaisesYanbal();
    }

    if (lstpaises != null && !lstpaises.isEmpty()) {
      paisDao.inhabilitarPaises();
      for (Pais pais : lstpaises) {
        paisDao.insertarOModificarPorCodigoPais(pais);
      }
      paisDao.generarFormatoArchivoPaises();
    }

    if (fechaActual.after(fechaUltima)) {
      parametro.setValor(FechaUtil.getFechaFormatoFecha(fechaActual));
      parametroBO.actualizarFechaUltimaActualizacion(parametro);
    }
  }

}
