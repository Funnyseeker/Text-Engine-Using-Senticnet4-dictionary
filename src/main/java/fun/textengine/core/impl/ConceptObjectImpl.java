package fun.textengine.core.impl;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class ConceptObjectImpl implements ConceptObject {
    String concept;
    List<String> semantics;
    List<String> moodtags;
    SenticsObject conceptSentics;
    PolarityObject conceptPolarity;
    ConceptType conceptType;

    public ConceptObjectImpl(String concept, List<String> semantics, List<String> moodtags,
                             SenticsObject conceptSentics, PolarityObject conceptPolarity) {
        this.concept = concept;
        this.semantics = semantics;
        this.moodtags = moodtags;
        this.conceptSentics = conceptSentics;
        this.conceptPolarity = conceptPolarity;
        String[] parts = concept.split("/");
        if (parts[parts.length - 1].contains("_")) {
            conceptType = ConceptType.COMPLEX;
        } else {
            conceptType = ConceptType.SIMPLE;
        }
    }

    @Override
    public String getConcept() {
        return concept;
    }

    @Override
    public String getText() {
        String[] parts = concept.split("/");
        return parts[parts.length - 1].replace("_", " ");
    }

    @Override
    public List<String> getSemantics() {
        return semantics;
    }

    @Override
    public List<String> getMoodtags() {
        return moodtags;
    }

    @Override
    public SenticsObject getSentics() {
        return conceptSentics;
    }

    @Override
    public PolarityObject getConceptPolarity() {
        return conceptPolarity;
    }

    @Override
    public ConceptType getConceptType() {
        return conceptType;
    }

    @Override
    public String toString() {
        String pattern = " concept: {0} {1}";
        return MessageFormat.format(pattern, getText(), conceptPolarity);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConceptObject) {
            return this.concept.equals(((ConceptObject) obj).getConcept());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.concept.hashCode();
    }

}
