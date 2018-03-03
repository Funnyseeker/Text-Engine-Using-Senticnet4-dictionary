package fun.textengine.core.impl;

import fun.textengine.core.Polarity;
import fun.textengine.core.PolarityObject;

import java.text.MessageFormat;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class PolarityObjectImpl implements PolarityObject {
    Polarity polarity;
    float intensity;

    public PolarityObjectImpl(Polarity polarity, float intensity) {
        this.polarity = polarity;
        this.intensity = intensity;
    }

    @Override
    public Polarity getPolarity() {
        return polarity;
    }

    @Override
    public float getIntensity() {
        return intensity;
    }

    @Override
    public String toString(){
        String pattern = "(polarity: {0}; intencity: {1})";
        return MessageFormat.format(pattern, polarity.name(), intensity);
    }
}
