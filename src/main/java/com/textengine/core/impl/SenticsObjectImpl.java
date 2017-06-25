package com.textengine.core.impl;

import com.textengine.core.SenticsObject;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class SenticsObjectImpl implements SenticsObject {
    float pleasantness;
    float attention;
    float sensitivity;
    float aptitude;

    public SenticsObjectImpl(float pleasantness, float attention, float sensitivity, float aptitude) {
        this.pleasantness = pleasantness;
        this.attention = attention;
        this.sensitivity = sensitivity;
        this.aptitude = aptitude;
    }

    @Override
    public float getPleasantness() {
        return pleasantness;
    }

    @Override
    public float getAttention() {
        return attention;
    }

    @Override
    public float getSensitivity() {
        return sensitivity;
    }

    @Override
    public float getAptitude() {
        return aptitude;
    }
}
