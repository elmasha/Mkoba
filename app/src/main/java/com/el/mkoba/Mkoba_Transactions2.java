package com.el.mkoba;

import java.util.Date;

public class Mkoba_Transactions2 {

     private String Recipient_No,Dead_line,Description, User_ID, Mkoba_ID, Category,Payment_Id,Payment_Title,Trans_UID;
     private double Amount, Total_price;
     private int Status_percent;
     private int Payment_status;
    private int Status;
    private int count;
     public Date timestamp;

    public Mkoba_Transactions2() {
    }

    public Mkoba_Transactions2(String recipient_No, String dead_line, String description, String user_ID, String mkoba_ID, String category, String payment_Id, String payment_Title, String trans_UID, double amount, double total_price, int status_percent, int payment_status, int status, int count, Date timestamp) {
        Recipient_No = recipient_No;
        Dead_line = dead_line;
        Description = description;
        User_ID = user_ID;
        Mkoba_ID = mkoba_ID;
        Category = category;
        Payment_Id = payment_Id;
        Payment_Title = payment_Title;
        Trans_UID = trans_UID;
        Amount = amount;
        Total_price = total_price;
        Status_percent = status_percent;
        Payment_status = payment_status;
        Status = status;
        this.count = count;
        this.timestamp = timestamp;
    }

    public String getRecipient_No() {
        return Recipient_No;
    }

    public void setRecipient_No(String recipient_No) {
        Recipient_No = recipient_No;
    }

    public String getDead_line() {
        return Dead_line;
    }

    public void setDead_line(String dead_line) {
        Dead_line = dead_line;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPayment_Id() {
        return Payment_Id;
    }

    public void setPayment_Id(String payment_Id) {
        Payment_Id = payment_Id;
    }

    public String getPayment_Title() {
        return Payment_Title;
    }

    public void setPayment_Title(String payment_Title) {
        Payment_Title = payment_Title;
    }

    public String getTrans_UID() {
        return Trans_UID;
    }

    public void setTrans_UID(String trans_UID) {
        Trans_UID = trans_UID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(double total_price) {
        Total_price = total_price;
    }

    public int getStatus_percent() {
        return Status_percent;
    }

    public void setStatus_percent(int status_percent) {
        Status_percent = status_percent;
    }

    public int getPayment_status() {
        return Payment_status;
    }

    public void setPayment_status(int payment_status) {
        Payment_status = payment_status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
