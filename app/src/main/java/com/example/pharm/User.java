package com.example.pharm;

public class User {
    String Name;
    String DOB;
    String City;
    String Gender;
    String SStatus;
    String email;
    String password;

    public User(String name, String DOB, String city, String gender, String SStatus, String email, String password) {
        Name = name;
        this.DOB = DOB;
        City = city;
        Gender = gender;
        this.SStatus = SStatus;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", DOB='" + DOB + '\'' +
                ", City='" + City + '\'' +
                ", Gender='" + Gender + '\'' +
                ", SStatus='" + SStatus + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getSStatus() {
        return SStatus;
    }

    public void setSStatus(String SStatus) {
        this.SStatus = SStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
