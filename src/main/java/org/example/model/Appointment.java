package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Appointment {
  private int id;
  private String date;
  private String time;
  private int hairdresserId;
  private String hairdresserInfo;
  private int clientId;
  private String clientInfo;
  private int serviceId;
  private String serviceInfo;
  private String status;

  public Appointment(int id, String date, String time, int hairdresserId) {
    this.id = id;
    this.date = date;
    this.time = time;
    this.hairdresserId = hairdresserId;
    status = "available";
    clientId = serviceId = -1;
    clientInfo = serviceInfo = "";
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getHairdresserId() {
    return hairdresserId;
  }

  public void setHairdresserId(int hairdresserId) {
    this.hairdresserId = hairdresserId;
  }

  public String getHairdresserInfo() {
    return hairdresserInfo;
  }

  public void setHairdresserInfo(String hairdresserInfo) {
    this.hairdresserInfo = hairdresserInfo;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public String getClientInfo() {
    return clientInfo;
  }

  public void setClientInfo(String clientInfo) {
    this.clientInfo = clientInfo;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }

  public String getServiceInfo() {
    return serviceInfo;
  }

  public void setServiceInfo(String serviceInfo) {
    this.serviceInfo = serviceInfo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getFormattedDate() {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    LocalDate date = LocalDate.parse(getDate(), inputFormatter);
    return date;
  }
}
