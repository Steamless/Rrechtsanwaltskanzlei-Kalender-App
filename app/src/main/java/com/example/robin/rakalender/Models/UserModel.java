package com.example.robin.rakalender.Models;

public class UserModel {

//    {
//        "user_id",
//            "name",
//            "e_mail",
//            "pass",
//            "state_id"
//    }

    private Integer user_id;
    private String name;
    private String e_mail;
    private String pass;
    private Integer state_id;

    public UserModel(Integer user_id, String name, String e_mail, String pass, Integer state_id) {

        this.user_id = user_id;
        this.name = name;
        this.e_mail = e_mail;
        this.pass = pass;
        this.state_id = state_id;

    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }
}
