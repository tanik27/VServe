package com.vserve2019.vserve.Model;

public class Userfetch {
    public String Name;
    public String Phone;

    public Userfetch() {
    }

    public Userfetch(String name, String phone) {
        Name = name;
        Phone = phone;
    }

    @Override
    public String toString() {
        return "Userfetch{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
