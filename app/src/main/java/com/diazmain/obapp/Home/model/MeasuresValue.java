package com.diazmain.obapp.Home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasuresValue {

    @SerializedName("peso")
    @Expose
    private double peso;

    @SerializedName("cintura")
    @Expose
    private double cintura;

    @SerializedName("grasa")
    @Expose
    private double grasa;

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public double getGrasa() {
        return grasa;
    }

    public void setGrasa(double grasa) {
        this.grasa = grasa;
    }
}
