package com.yanbal.kiosko.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yanbal.kiosko.dao.RolDao;
import com.yanbal.kiosko.entities.Rol;

/**
 * Clase de negocio de Rol, en esta clase se implementa los metodos para la gestion de los roles.
 * 
 * 
 */
@Service
public class RolBO {

  @Autowired
  private RolDao rolDao;

  /**
   * Metodo de negocio que obtiene todos los roles de la base de datos.
   * 
   * @return retorna un objecto de lista, contiene todos los roles desde la base de datos.
   * @see List<Rol> objeto tipo lista.
   */
  @SuppressWarnings("rawtypes")
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
      isolation = Isolation.READ_COMMITTED)
  public List<Rol> listarRoles() {
    return rolDao.listarTodo();
  }

}
