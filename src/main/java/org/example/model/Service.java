package org.example.model;

public class Service {
  private int serviceId;
  private String type;
  private double price;

  public Service(int serviceId, String type, double price) {
    this.serviceId = serviceId;
    this.type = type;
    this.price = price;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
