package com.yanbal.kiosko.common.json.request;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura de la cabecera de los request JSON que se reciben en los
 * servicios
 * 
 * @author alex.contreras
 * 
 */
public class JsonKioskoHeaderRequest {

  @SerializedName("ApplicationId")
  private String applicationId;

  @SerializedName("ServiceName")
  private String serviceName;

  public String getApplicationId() {
    return applicationId;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

}
