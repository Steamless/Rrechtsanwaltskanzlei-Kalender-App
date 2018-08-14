package com.example.robin.rakalender.Models;

import java.util.ArrayList;

public class ClientReadResponse {

    private boolean ok;
    private ArrayList<ClientModel> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<ClientModel> getData() {
        return data;
    }

    public void setData(ArrayList<ClientModel> data) {
        this.data = data;
    }

}
