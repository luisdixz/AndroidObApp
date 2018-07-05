package com.diazmain.obapp.Home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author luisdixz1
 * <p>Esta clase esta pendiente...</p>
 */
public class LastMeasures {

    /*@SerializedName("measures")
    private List<MeasuresValue> measures;

    public List<MeasuresValue> getMeasures() {
        return measures;
    }*/

    private MeasuresValue lastMonth;
    private MeasuresValue currentMonth;
    private int months;

    public LastMeasures(MeasuresValue _last, MeasuresValue _current, int _months) {
        this.lastMonth = _last;
        this.currentMonth = _current;
        this.months = _months;
    }

    public MeasuresValue getLastMonth() {
        return lastMonth;
    }

    public MeasuresValue getCurrentMonth() {
        return currentMonth;
    }

    public int getMonths() {
        return months;
    }
}
