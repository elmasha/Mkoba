package com.el.mkoba;

import java.util.Date;


public class Transactions {

    private String User_name, Mkoba_ID,User_Id;
    private String transaction_type;
    private double amount;
    private double currentBalance;
    public Date timestamp;


    public Transactions(){
        //Empty Constructor
    }

    public Transactions(String user_name, String mkoba_ID, String user_Id,
                        String transaction_type, double amount, double currentBalance, Date timestamp) {
        User_name = user_name;
        Mkoba_ID = mkoba_ID;
        User_Id = user_Id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.timestamp = timestamp;
    }


    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getMkoba_ID() {
        return Mkoba_ID;
    }

    public void setMkoba_ID(String mkoba_ID) {
        Mkoba_ID = mkoba_ID;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}



