package com.diazmain.obapp.Home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class MeasuresValue {

    @SerializedName("peso")
    @Expose
    private BigDecimal peso;

    @SerializedName("cintura")
    @Expose
    private BigDecimal cintura;

    @SerializedName("grasa")
    @Expose
    private BigDecimal grasa;

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getCintura() {
        return cintura;
    }

    public void setCintura(BigDecimal cintura) {
        this.cintura = cintura;
    }

    public BigDecimal getGrasa() {
        return grasa;
    }

    public void setGrasa(BigDecimal grasa) {
        this.grasa = grasa;
    }
}
