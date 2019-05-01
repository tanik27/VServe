package com.vserve2019.vserve.Model;
public class Services {
    public String Name;
    public String Price;
    public Services() {
    }
    public Services(String name, String price) {
        Name = name;
        Price = price;
    }
    @Override
    public String toString() {
        return "Name=:" + Name +
                "\t Price=" + Price ;
    }
}

