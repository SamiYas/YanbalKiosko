package com.yanbal.lib.corp.bo;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.yanbal.lib.corp.dao.PushEnvioDao;
import com.yanbal.lib.corp.entities.PushEnvio;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Clase de negocio que gestiona el registro del resultado de los envios push.
 * 
 * @author lennin.davila
 *
 */
public class PushEnvioBO {

  PushEnvioDao pushEnvioDao;

  /**
   * Constructor, inicializa la conexion a la base de datos en el DAO segun el nombre de JNDI
   * recibido
   */
  public PushEnvioBO(String datasourcename) throws NamingException {
    pushEnvioDao = new PushEnvioDao();
    pushEnvioDao.inicializarDB(datasourcename);
  }

  /**
   * Metodo que inserta el registro de push.
   * @throws Exception 
   */
  public Long insertarPushEnvio(PushEnvio pushEnvio) throws LibCorpException,SQLException {
    return pushEnvioDao.insertar(pushEnvio);
  }
}
