package me.vinhdo.androidsuppordesign.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinh on 10/15/15.
 */
public class SubModel {
    @SerializedName("Label")
    private String label;
    @SerializedName("Source")
    private String source;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
