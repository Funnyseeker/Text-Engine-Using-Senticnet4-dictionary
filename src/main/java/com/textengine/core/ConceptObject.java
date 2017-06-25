package com.textengine.core;

import java.util.List;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface ConceptObject {
    enum ConceptType {
        SIMPLE,
        COMPLEX
    }

    String getConcept();

    String getText();

    List<String> getSemantics();

    List<String> getMoodtags();

    SenticsObject getSentics();

    PolarityObject getConceptPolarity();

    ConceptType gerConceptType();
}
