package com.dannextech.apps.daktari_online.model;

public class PharmacyModel {
    private String county, description, email, lattitude, longitude, licence, name, password, phone, supfname, suplicense, suplname, supsurname, town;

    public PharmacyModel() {
    }

    public PharmacyModel(String county, String description, String email, String lattitude, String longitude, String licence, String name, String password, String phone, String supfname, String suplicense, String suplname, String supsurname, String town) {
        this.county = county;
        this.description = description;
        this.email = email;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.licence = licence;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.supfname = supfname;
        this.suplicense = suplicense;
        this.suplname = suplname;
        this.supsurname = supsurname;
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSupfname() {
        return supfname;
    }

    public void setSupfname(String supfname) {
        this.supfname = supfname;
    }

    public String getSuplicense() {
        return suplicense;
    }

    public void setSuplicense(String suplicense) {
        this.suplicense = suplicense;
    }

    public String getSuplname() {
        return suplname;
    }

    public void setSuplname(String suplname) {
        this.suplname = suplname;
    }

    public String getSupsurname() {
        return supsurname;
    }

    public void setSupsurname(String supsurname) {
        this.supsurname = supsurname;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
