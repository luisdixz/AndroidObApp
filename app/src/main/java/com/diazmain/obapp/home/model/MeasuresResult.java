package com.diazmain.obapp.home.model;

import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class MeasuresResult {

    @SerializedName("measures")
    private List<MeasuresValue> measures;

    public List<MeasuresValue> getMeasures() {
        return measures;
    }
}
