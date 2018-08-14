package com.example.robin.rakalender.Models;

public class TerminTypModel {


    private int termin_id_typ;
    private String description;


    @Override
    public String toString() {
        return description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTermin_id_typ() {
        return termin_id_typ;
    }

    public void setTermin_id_typ(int termin_id_typ) {
        this.termin_id_typ = termin_id_typ;
    }
}
