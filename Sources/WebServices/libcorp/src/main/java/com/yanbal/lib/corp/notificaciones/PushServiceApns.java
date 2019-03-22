package com.yanbal.lib.corp.notificaciones;

import com.yanbal.lib.corp.interfaces.FactoryPushMessager;
import com.yanbal.lib.corp.interfaces.IPushService;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Factory de notificaciones IOS
 * 
 * @author lennin.davila
 *
 */
public class PushServiceApns extends FactoryPushMessager {

  /**
   * retorna la instancia factory correcta
   */
  @Override
  public IPushService getPushService() throws LibCorpException {
    return new PushApns();
  }
}