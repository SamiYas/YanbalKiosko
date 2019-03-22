package com.yanbal.kiosko.common.json.response;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura de la cabecera de los response JSON que se devuelven en
 * los servicios
 * 
 * @author alex.contreras
 * 
 */
public class JsonKioskoHeaderResponse {

  @SerializedName("ApplicationId")
  private String applicationId;

  @SerializedName("ServiceName")
  private String serviceName;

  @SerializedName("ResponseCode")
  private String responseCode;

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

  public String getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

}
