package com.yanbal.lib.corp.interfaces;

import java.util.List;

import com.yanbal.lib.corp.entities.MultiResultadoPush;
import com.yanbal.lib.corp.log.LibCorpException;

/**
 * Interface para el envio de notificaciones push
 * 
 * @author lennin.davila
 *
 */
public interface IPushService {
  /**
   * firma del metodo que envia notificaciones push
   */
  public MultiResultadoPush push(List<String> tokenDevice, String message, int reintento);
}
