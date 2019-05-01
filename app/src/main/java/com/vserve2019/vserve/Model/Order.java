package com.vserve2019.vserve.Model;
public class Order {
    public String Location;
    public String MechanicName;
    public String MechanicPhone;
    public String MechanicAddress;
    public String Service;
    public String Total;
    public Order() {
    }
    public Order(String location, String mechanicName, String mechanicPhone, String mechanicAddress, String service, String total) {
        Location = location;
        MechanicName = mechanicName;
        MechanicPhone = mechanicPhone;
        MechanicAddress = mechanicAddress;
        Service = service;
        Total = total;
    }
    @Override
    public String toString() {
        return "Order{" +
                "Location='" + Location + '\'' +
                ", MechanicName='" + MechanicName + '\'' +
                ", MechanicPhone='" + MechanicPhone + '\'' +
                ", MechanicAddress='" + MechanicAddress + '\'' +
                ", Service='" + Service + '\'' +
                ", Total='" + Total + '\'' +
                '}';
    }
}
