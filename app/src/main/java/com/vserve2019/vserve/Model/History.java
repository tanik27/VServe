package com.vserve2019.vserve.Model;
public class History {
    int id;
    public String Location;
    public String MechanicName;
    public String MechanicPhone;
    public String MechanicAddress;
    public String Service;
    public String docId;
    public History() {
    }
    public History(int id,String location, String mechanicName, String mechanicPhone, String mechanicAddress, String service, String docid) {
        id=id;
        Location = location;
        MechanicName = mechanicName;
        MechanicPhone = mechanicPhone;
        MechanicAddress = mechanicAddress;
        Service = service;
        docId=docid;
    }
    @Override
    public String toString() {
        return "History{" +
                "Location='" + Location + '\'' +
                ", MechanicName='" + MechanicName + '\'' +
                ", MechanicPhone='" + MechanicPhone + '\'' +
                ", MechanicAddress='" + MechanicAddress + '\'' +
                ", Service='" + Service + '\'' +
                '}';
    }
}

