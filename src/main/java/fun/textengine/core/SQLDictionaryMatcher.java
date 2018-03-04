package fun.textengine.core;


import fun.textengine.core.utils.SQLDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLDictionaryMatcher implements ConceptMatcher {
    @Override
    public Map<ConceptObject, Integer> getMatches(String text) {
        Map<ConceptObject, Integer> matched = new HashMap<>();
        String[] words = text.split("[,;:.!?\\s]+");
        for (String word : words) {
            List<ConceptObject> complexList = SQLDictionary.getInstance().getComplexConceptObjects(word);
            text = check(complexList, text, matched);
            List<ConceptObject> simpleList = SQLDictionary.getInstance().getConceptObjects(word);
            text = check(simpleList, text, matched);
        }
        return matched;
    }

    private String check(List<ConceptObject> concepts, String text, Map<ConceptObject, Integer> matched) {
        for (ConceptObject concept : concepts) {
            while (text.contains(" " + concept.getText().toLowerCase() + " ")) {
                text = text.replaceFirst(" " + concept.getText().toLowerCase() + " ", " | ");
                if (!matched.containsKey(concept)) {
                    matched.put(concept, 1);
                } else {
                    int count = matched.get(concept) + 1;
                    matched.put(concept, count);
                }
            }
        }
        return text;
    }
}
