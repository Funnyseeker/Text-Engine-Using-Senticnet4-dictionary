package fun.textengine.core.utils;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.Polarity;
import fun.textengine.core.TextObject;
import fun.textengine.core.impl.PolarityObjectImpl;
import fun.textengine.core.impl.TextObjectImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Funnyseeker on 22.05.2017.
 */
public class TextEngineParser {

    public TextObject parseText(String text) {
        String originalText = text;
        text = text.toLowerCase();
        Map<ConceptObject, Integer> matched = getMatchedBySQLDictionary(text);
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

    private List<ConceptObject> getMatchedByMapDictionary(String text) {
        List<ConceptObject> matched = new ArrayList<>();
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getComplexConeptsDictionary().entrySet()) {
            if (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replace(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                matched.add(entry.getValue());
            }
        }
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getSimpleConceptsDictionary().entrySet()) {
            if (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replace(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                matched.add(entry.getValue());
            }
        }
        return matched;
    }

    private Map<ConceptObject, Integer> getMatchedBySQLDictionary(String text) {
        return SQLDictionary.getInstance().getConceptObjects(text);
    }
}
