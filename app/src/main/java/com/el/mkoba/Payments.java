package com.el.mkoba;

import java.util.Date;

public class Payments {

    private String User_name, payment_method, payment_description, mkoba_ID_id, User_id;
    private double amount;
    private Date timestamp;

    public Payments() {
        //empty constructor
    }


    public Payments(String user_name, String payment_method, String payment_description,
                    String mkoba_ID_id, String user_id, double amount, Date timestamp) {
        User_name = user_name;
        this.payment_method = payment_method;
        this.payment_description = payment_description;
        this.mkoba_ID_id = mkoba_ID_id;
        User_id = user_id;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_description() {
        return payment_description;
    }

    public void setPayment_description(String payment_description) {
        this.payment_description = payment_description;
    }

    public String getMkoba_ID_id() {
        return mkoba_ID_id;
    }

    public void setMkoba_ID_id(String mkoba_ID_id) {
        this.mkoba_ID_id = mkoba_ID_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}