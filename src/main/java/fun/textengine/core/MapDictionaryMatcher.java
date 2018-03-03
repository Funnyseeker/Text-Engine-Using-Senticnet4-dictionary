package fun.textengine.core;

import fun.textengine.core.utils.MapDictionary;

import java.util.HashMap;
import java.util.Map;

public class MapDictionaryMatcher implements ConceptMatcher {

    @Override
    public Map<ConceptObject, Integer> getMatches(String text) {
        Map<ConceptObject, Integer> matched = new HashMap<>();
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getComplexConeptsDictionary()
                .entrySet()) {
            if (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replaceFirst(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                if (!matched.containsKey(entry.getValue())) {
                    matched.put(entry.getValue(), 1);
                } else {
                    int count = matched.get(entry.getValue()) + 1;
                    matched.put(entry.getValue(), count);
                }
            }
        }
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getSimpleConceptsDictionary()
                .entrySet()) {
            if (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replaceFirst(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                if (!matched.containsKey(entry.getValue())) {
                    matched.put(entry.getValue(), 1);
                } else {
                    int count = matched.get(entry.getValue()) + 1;
                    matched.put(entry.getValue(), count);
                }
            }
        }
        return matched;
    }
}
