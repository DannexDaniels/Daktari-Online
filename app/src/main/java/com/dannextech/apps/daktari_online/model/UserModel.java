package com.dannextech.apps.daktari_online.model;

public class UserModel {
    private String idno, phone, password, type, status;

    public UserModel() {
    }

    public UserModel(String idno, String phone, String password, String type, String status) {
        this.idno = idno;
        this.phone = phone;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
