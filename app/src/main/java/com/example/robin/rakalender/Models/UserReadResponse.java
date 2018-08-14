package com.example.robin.rakalender.Models;

import java.util.ArrayList;

public class UserReadResponse {

    private boolean ok;
    private ArrayList<UserModel> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<UserModel> getData() {
        return data;
    }

    public void setData(ArrayList<UserModel> data) {
        this.data = data;
    }

}
