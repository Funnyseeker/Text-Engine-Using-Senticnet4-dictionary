package com.textengine.core;

import org.w3c.dom.Node;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface ConceptObjectFactory {

    ConceptObject createNewConceptObject(Node node, SenticsObject senticsObject, PolarityObject polarityObject);
}
