package com.yanbal.lib.corp.interfaces;

import com.yanbal.lib.corp.enums.Plataforma;
import com.yanbal.lib.corp.log.LibCorpException;
import com.yanbal.lib.corp.notificaciones.PushServiceApns;
import com.yanbal.lib.corp.notificaciones.PushServiceGcm;
import com.yanbal.lib.corp.notificaciones.PushServiceMpns;

/**
 * Clase Factory principal de envio de notificiaciones Push
 * 
 * @author lennin.davila
 *
 */
public abstract class FactoryPushMessager {

  public abstract IPushService getPushService() throws LibCorpException;

  /**
   * retorna la instancia factory correcta segun la plataforma
   */
  public static FactoryPushMessager getPushFactory(Plataforma Factory) {
    switch (Factory) {
      case IOS_PLATFORM:
        return new PushServiceApns();
      case ANDROID_PLATFORM:
        return new PushServiceGcm();
      default:
        return new PushServiceMpns();
    }   
  }
}
