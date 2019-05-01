package com.vserve2019.vserve.Model;
public class Mechanic {
    public String Name;
    public String Phone;
    public String Address;
    public Mechanic() {
    }
    public Mechanic(String name, String phone, String address) {
        Name = name;
        Phone = phone;
        Address = address;
    }
    @Override
    public String toString() {
        return "Mechanic{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
