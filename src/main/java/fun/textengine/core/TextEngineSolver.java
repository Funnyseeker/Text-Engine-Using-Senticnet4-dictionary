package fun.textengine.core;

import fun.textengine.core.impl.PolarityObjectImpl;
import fun.textengine.core.impl.TextObjectImpl;

import java.util.Map;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public class TextEngineSolver {

    private ConceptMatcher matcher;

    public TextEngineSolver(ConceptMatcher matcher) {
        this.matcher = matcher;
    }

    public TextObject parseText(String text) {
        String originalText = text;
        text = text.toLowerCase();
        Map<ConceptObject, Integer> matched = matcher.getMatches(text);
        String positiveGroup = "";
        String negativeGroup = "";


        float sum = 0;
        int del = 0;
        for (Map.Entry<ConceptObject, Integer> entry : matched.entrySet()) {
            del += entry.getValue();
            sum = sum + (entry.getKey().getConceptPolarity().getIntensity() * entry.getValue());
            if (entry.getKey().getConceptPolarity().getPolarity() == Polarity.POSITIVE) {
                positiveGroup += entry.getKey().getText() + "; ";
            } else {
                negativeGroup += entry.getKey().getText() + "; ";
            }
        }
        if (del != 0) {
            sum = sum / del;
        }
        Polarity polarity = Polarity.getFromIntesity(sum);
        return new TextObjectImpl(originalText, new PolarityObjectImpl(polarity, sum), positiveGroup, negativeGroup);
    }

    public ConceptMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(ConceptMatcher matcher) {
        this.matcher = matcher;
    }
}
