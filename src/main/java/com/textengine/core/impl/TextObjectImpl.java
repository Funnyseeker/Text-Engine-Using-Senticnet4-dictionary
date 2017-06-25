package com.textengine.core.impl;

import com.textengine.core.PolarityObject;
import com.textengine.core.TextObject;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public class TextObjectImpl implements TextObject {
    String text;
    PolarityObject polarityObject;
    String positiveGroup;
    String negativeFroup;

    public TextObjectImpl(String text, PolarityObject polarityObject, String positiveGroup, String negativeFroup) {
        this.text = text;
        this.polarityObject = polarityObject;
        this.positiveGroup = positiveGroup;
        this.negativeFroup = negativeFroup;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public PolarityObject getPolarityObject() {
        return polarityObject;
    }

    @Override
    public String getPositiveGroup() {
        return positiveGroup;
    }

    @Override
    public String getNegativeGroup() {
        return negativeFroup;
    }
}
