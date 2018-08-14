package com.example.robin.rakalender.Models;

import java.util.ArrayList;

public class TerminReadResponse {

    private boolean ok;
    private ArrayList<TerminModel> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<TerminModel> getData() {
        return data;
    }

    public void setData(ArrayList<TerminModel> data) {
        this.data = data;
    }
}
