package com.example.robin.rakalender.Models;

public class ClientModel {

//    {
//        "client_id",
//            "name",
//            "bday",
//            "adresse",
//            "phone",
//            "e_mail",
//            "state_id"
//    }

    private Integer client_id;
    private String name;
    private String bday;
    private String adresse;
    private String phone;
    private String e_mail;
    private Integer state_id;

    public ClientModel (Integer client_id, String name, String bday, String adresse, String phone, String e_mail, Integer state_id) {
        this.client_id = client_id;
        this.name = name;
        this.bday = bday;
        this.adresse = adresse;
        this.phone = phone;
        this.e_mail = e_mail;
        this.state_id = state_id;

    }


    @Override
    public String toString() {
        return name;
    }


    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }
}
