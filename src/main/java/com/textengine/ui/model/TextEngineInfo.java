package com.textengine.ui.model;

import javafx.beans.property.*;

/**
 * Created by Funnyseeker on 21.05.2017.
 */

public class TextEngineInfo {
    private final IntegerProperty id;
    private final StringProperty text;
    private final FloatProperty intensity;
    private final StringProperty polarity;
    private final StringProperty negativeGroup;
    private final StringProperty positiveGroup;

    public TextEngineInfo(Integer id, String text, Float intensity, String polarity,
                          String negativeGroup, String positiveGroup) {
        this.id = new SimpleIntegerProperty(id);
        this.text = new SimpleStringProperty(text);
        this.intensity = new SimpleFloatProperty(intensity);
        this.polarity = new SimpleStringProperty(polarity);
        this.negativeGroup = new SimpleStringProperty(negativeGroup);
        this.positiveGroup = new SimpleStringProperty(positiveGroup);
    }

    public TextEngineInfo() {
        this(-1, null, 0f, null, null, null);
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public Float getIntensity() {
        return intensity.get();
    }

    public void setIntensity(Float intensity) {
        this.intensity.set(intensity);
    }

    public FloatProperty intensityProperty() {
        return intensity;
    }

    public String getPolarity() {
        return polarity.get();
    }

    public void setPolarity(String polarity) {
        this.polarity.set(polarity);
    }

    public StringProperty polarityProperty() {
        return polarity;
    }

    public void setNegativeGroup(String negativeGroup) {
        this.negativeGroup.set(negativeGroup);
    }

    public String getNegativeGroup() {
        return negativeGroup.get();
    }

    public StringProperty negativeGroupProperty() {
        return negativeGroup;
    }

    public void setPositiveGroup(String positiveGroup) {
        this.positiveGroup.set(positiveGroup);
    }

    public String getPositiveGroup() {
        return positiveGroup.get();
    }

    public StringProperty positiveGroupProperty() {
        return positiveGroup;
    }
}
