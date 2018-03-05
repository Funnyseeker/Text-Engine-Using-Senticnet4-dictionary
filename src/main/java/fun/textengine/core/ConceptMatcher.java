package fun.textengine.core;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.utils.ProgressTraker;

import java.util.Map;

public interface ConceptMatcher {
    Map<ConceptObject, Integer> getMatches(String text);
    ProgressTraker getProgressTraker();
    void setProgressTraker(ProgressTraker progressTraker);
}