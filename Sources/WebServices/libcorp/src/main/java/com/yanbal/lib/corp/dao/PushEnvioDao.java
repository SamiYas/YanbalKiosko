package com.yanbal.lib.corp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.yanbal.lib.corp.entities.PushEnvio;
import com.yanbal.lib.corp.log.LibCorpException;


/**
 * Clase DAO que registra del resultado de los envios push.
 * 
 * @author lennin.davila
 *
 */
public class PushEnvioDao {

  DataSource datasource;
  Context context;

  /**
   * Constructor vacio por defecto
   */
  public PushEnvioDao() {    
  }

  /**
   * Constructor que inicializa la conexion a la base de datos en el DAO segun el nombre de JNDI
   * recibido
   */
  public void inicializarDB(String datasourcename) throws NamingException {
    context = new InitialContext();
    datasource = (DataSource) context.lookup(datasourcename);
  }

  /**
   * Metodo que inserta el registro de push en la base de datos.
   * @throws SQLException 
   * @throws Exception 
   */
  public Long insertar(PushEnvio pushEnvio) throws LibCorpException, SQLException {
    Connection con = null;
    PreparedStatement pstmt;
    Long rows = 0L;
    try {
      con = datasource.getConnection();

      con.setAutoCommit(false);
      pstmt = con.prepareStatement("{CALL KSK_KIOSKO.USP_PUSH_ENVIO_INS(?,?,?,?,?,?,?,?)}");
      pstmt.setString(1, pushEnvio.getMensaje());
      pstmt.setDate(2, new Date(pushEnvio.getFechaHoraInicio().getTime()));
      pstmt.setDate(3, new Date(pushEnvio.getFechaHoraInicio().getTime()));
      pstmt.setLong(4, pushEnvio.getExitosos());
      pstmt.setLong(5, pushEnvio.getErrores());
      pstmt.setString(6, pushEnvio.getMotivoError());
      pstmt.setString(7, pushEnvio.getPlataforma());
      pstmt.setString(8, pushEnvio.getPais());
      rows = (long) pstmt.executeUpdate();
      con.commit();
      pstmt.close();
    } catch (Exception ex) {
      throw new LibCorpException(ex.getMessage(),ex);
    } finally {
      if (con != null)
        con.close();
    }
    return rows;
  }
}
