package com.el.mkoba;

import java.util.Date;

public class All_Transactions {

    private String recipient_number,transaction_deadline,transaction_description,user_id;
    private double amount;
    private String mkoba_ID;
    private double total_price;
    private String  account_type,status;
    public Date timestamp;


    public All_Transactions() {
    }


    public All_Transactions(String recipient_number, String transaction_deadline, String transaction_description, String user_id, double amount,
                            String mkoba_ID, double total_price, String account_type, String status, Date timestamp) {
        this.recipient_number = recipient_number;
        this.transaction_deadline = transaction_deadline;
        this.transaction_description = transaction_description;
        this.user_id = user_id;
        this.amount = amount;
        this.mkoba_ID = mkoba_ID;
        this.total_price = total_price;
        this.account_type = account_type;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getRecipient_number() {
        return recipient_number;
    }

    public void setRecipient_number(String recipient_number) {
        this.recipient_number = recipient_number;
    }

    public String getTransaction_deadline() {
        return transaction_deadline;
    }

    public void setTransaction_deadline(String transaction_deadline) {
        this.transaction_deadline = transaction_deadline;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMkoba_ID() {
        return mkoba_ID;
    }

    public void setMkoba_ID(String mkoba_ID) {
        this.mkoba_ID = mkoba_ID;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
