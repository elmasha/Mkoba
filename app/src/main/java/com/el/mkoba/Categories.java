package com.el.mkoba;

public class Categories {

    private String category_name,Doc_id;

    public Categories() {
        //empty
    }

    public Categories(String category_name, String doc_id) {
        this.category_name = category_name;
        Doc_id = doc_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDoc_id() {
        return Doc_id;
    }

    public void setDoc_id(String doc_id) {
        Doc_id = doc_id;
    }
}
