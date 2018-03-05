package fun.textengine.ui.model;

import fun.textengine.core.ConceptObject;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

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

    public TextEngineInfo(Integer id, String text,
                          List<ConceptObject> property,
                          Float intensity, String polarity,
                          String negativeGroup, String positiveGroup) {
        this.id = new SimpleIntegerProperty(id);
        this.text = new SimpleStringProperty(text);
        this.intensity = new SimpleFloatProperty(intensity);
        this.polarity = new SimpleStringProperty(polarity);
        this.negativeGroup = new SimpleStringProperty(negativeGroup);
        this.positiveGroup = new SimpleStringProperty(positiveGroup);
    }

    public TextEngineInfo() {
        this(-1, null, null, 0f, null, null, null);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
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

    public String getNegativeGroup() {
        return negativeGroup.get();
    }

    public void setNegativeGroup(String negativeGroup) {
        this.negativeGroup.set(negativeGroup);
    }

    public StringProperty negativeGroupProperty() {
        return negativeGroup;
    }

    public String getPositiveGroup() {
        return positiveGroup.get();
    }

    public void setPositiveGroup(String positiveGroup) {
        this.positiveGroup.set(positiveGroup);
    }

    public StringProperty positiveGroupProperty() {
        return positiveGroup;
    }
}
