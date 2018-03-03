package fun.textengine.core;

import fun.textengine.core.ConceptObject;

import java.util.Map;

public interface ConceptMatcher {
    Map<ConceptObject, Integer> getMatches(String text);
}