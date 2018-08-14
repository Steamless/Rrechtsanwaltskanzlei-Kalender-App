package com.example.robin.rakalender.Models;

import java.util.Date;

public class TerminModel {

//    {
//        "termin_id": 2,
//            "user_id": 2,
//            "description": "Landgericht Leipzig Saal2",
//            "date_time_anfang": "2018-12-12T18:00:00.000Z",
//            "date_time_ende": "2018-12-12T19:00:00.000Z",
//            "termin_id_typ": 1,
//            "client_id": 1,
//            "state_id": 1,
//            "name": "kenneth",
//            "adresse": "guatemala",
//            "phone": "+502 44556969",
//            "e_mail": "ken@gmail.com"
//    }

    private Integer termin_id;
    private Integer user_id;
    private String description;
    private Integer state_id;
    private String name;
    private String adresse;
    private String phone;
    private Date date_time_anfang;
    private Date date_time_ende;
    private Integer termin_id_typ;
    private String termin_typ_description;

    //crear
    public TerminModel(Integer user_id, String description, Date date_time_anfang, Date date_time_ende, Integer termin_id_typ, Integer client_id) {
        this.user_id = user_id;
        this.description = description;
        this.date_time_anfang = date_time_anfang;
        this.date_time_ende = date_time_ende;
        this.termin_id_typ = termin_id_typ;
        this.client_id = client_id;
        this.state_id = 1;

    }

    public Date getDate_time_anfang() {
        return date_time_anfang;
    }

    public void setDate_time_anfang(Date date_time_anfang) {
        this.date_time_anfang = date_time_anfang;
    }

    public Date getDate_time_ende() {
        return date_time_ende;
    }

    public void setDate_time_ende(Date date_time_ende) {
        this.date_time_ende = date_time_ende;
    }



    public Integer getTermin_id_typ() {
        return termin_id_typ;
    }

    public void setTermin_id_typ(Integer termin_id_typ) {
        this.termin_id_typ = termin_id_typ;
    }



    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    private Integer client_id;

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }



    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    private String e_mail;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public Integer getTermin_id() {
        return termin_id;
    }

    public void setTermin_id(Integer termin_id) {
        this.termin_id = termin_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public String getTermin_typ_description() {
        return termin_typ_description;
    }

    public void setTermin_typ_description(String termin_typ_description) {
        this.termin_typ_description = termin_typ_description;
    }
}
