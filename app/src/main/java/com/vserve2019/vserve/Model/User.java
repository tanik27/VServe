package com.vserve2019.vserve.Model;
public class User {
    public String Name;
    public String Phone;
    public String Email;
    public String Password;
    public String Token;
    public User() {
    }
    public User(String name, String phone, String email, String password, String token) {
        Name = name;
        Phone = phone;
        Email = email;
        Password = password;
        Token = token;
    }
    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }


}
