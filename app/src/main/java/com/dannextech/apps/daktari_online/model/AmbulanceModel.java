package com.dannextech.apps.daktari_online.model;

public class AmbulanceModel {
    String category, county, description, driver,emailMain, emailOther, lattitude, longitude, organization, password, phoneMain, phoneOther, town, vehicleNo;

    public AmbulanceModel() {
    }

    public AmbulanceModel(String category, String county, String description, String driver, String emailMain, String emailOther, String lattitude, String longitude, String organization, String password, String phoneMain, String phoneOther, String town, String vehicleNo) {
        this.category = category;
        this.county = county;
        this.description = description;
        this.driver = driver;
        this.emailMain = emailMain;
        this.emailOther = emailOther;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.organization = organization;
        this.password = password;
        this.phoneMain = phoneMain;
        this.phoneOther = phoneOther;
        this.town = town;
        this.vehicleNo = vehicleNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getEmailMain() {
        return emailMain;
    }

    public void setEmailMain(String emailMain) {
        this.emailMain = emailMain;
    }

    public String getEmailOther() {
        return emailOther;
    }

    public void setEmailOther(String emailOther) {
        this.emailOther = emailOther;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneMain() {
        return phoneMain;
    }

    public void setPhoneMain(String phoneMain) {
        this.phoneMain = phoneMain;
    }

    public String getPhoneOther() {
        return phoneOther;
    }

    public void setPhoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
