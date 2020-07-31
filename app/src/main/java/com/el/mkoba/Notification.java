package com.el.mkoba;

import java.util.Date;

public class Notification {

   private String   Recipient_No,type, to, body, User_id, doc_ID;
   private Date timestamp;

    public Notification() {
        //empty
    }

    public Notification(String recipient_No, String type, String to, String body, String user_id, String doc_ID, Date timestamp) {
        Recipient_No = recipient_No;
        this.type = type;
        this.to = to;
        this.body = body;
        User_id = user_id;
        this.doc_ID = doc_ID;
        this.timestamp = timestamp;
    }


    public String getRecipient_No() {
        return Recipient_No;
    }

    public void setRecipient_No(String recipient_No) {
        Recipient_No = recipient_No;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getDoc_ID() {
        return doc_ID;
    }

    public void setDoc_ID(String doc_ID) {
        this.doc_ID = doc_ID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
