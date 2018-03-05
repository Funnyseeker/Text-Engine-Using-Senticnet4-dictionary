package fun.textengine.core;


import fun.textengine.core.utils.ProgressTraker;
import fun.textengine.dictionaries.SQLDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLDictionaryMatcher implements ConceptMatcher {

    private ProgressTraker progressTraker;

    @Override
    public Map<ConceptObject, Integer> getMatches(String text) {
        Map<ConceptObject, Integer> matched = new HashMap<>();
        String[] words = text.split("[,;:.!?\\s]+");
        if (progressTraker != null) {
            progressTraker.setProgressPointWeight(1.00 / words.length);
        }
        for (String word : words) {
            List<ConceptObject> complexList = SQLDictionary.getInstance().getComplexConceptObjects(word);
            text = check(complexList, text, matched);
            List<ConceptObject> simpleList = SQLDictionary.getInstance().getConceptObjects(word);
            text = check(simpleList, text, matched);
            if (progressTraker != null) {
                progressTraker.addProgressPoints(1);
            }
        }
        return matched;
    }


    @Override
    public ProgressTraker getProgressTraker() {
        return progressTraker;
    }

    @Override
    public void setProgressTraker(ProgressTraker progressTraker) {
        this.progressTraker = progressTraker;
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
