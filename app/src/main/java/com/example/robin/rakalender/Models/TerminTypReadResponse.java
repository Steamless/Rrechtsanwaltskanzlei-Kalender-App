package com.example.robin.rakalender.Models;

import java.util.ArrayList;

public class TerminTypReadResponse {

    private boolean ok;
    private ArrayList<TerminTypModel> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<TerminTypModel> getData() {
        return data;
    }

    public void setData(ArrayList<TerminTypModel> data) {
        this.data = data;
    }

}
