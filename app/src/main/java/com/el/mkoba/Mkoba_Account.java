package com.el.mkoba;

public class Mkoba_Account {

    private String First_Name,Last_Name,Location,Phone,Email,Id_No,User_ID,Mkoba_ID,Profile_Image;
    private double balance = 0.00;
    private double expences = 0.00;

    public Mkoba_Account() {
    }

    public Mkoba_Account(String first_Name, String last_Name, String location, String phone, String email, String id_No,
                         String user_ID, String mkoba_ID, String profile_Image, double balance, double expences) {
        First_Name = first_Name;
        Last_Name = last_Name;
        Location = location;
        Phone = phone;
        Email = email;
        Id_No = id_No;
        User_ID = user_ID;
        Mkoba_ID = mkoba_ID;
        Profile_Image = profile_Image;
        this.balance = balance;
        this.expences = expences;
    }


    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getId_No() {
        return Id_No;
    }

    public void setId_No(String id_No) {
        Id_No = id_No;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getMkoba_ID() {
        return Mkoba_ID;
    }

    public void setMkoba_ID(String mkoba_ID) {
        Mkoba_ID = mkoba_ID;
    }

    public String getProfile_Image() {
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        Profile_Image = profile_Image;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExpences() {
        return expences;
    }

    public void setExpences(double expences) {
        this.expences = expences;
    }
}