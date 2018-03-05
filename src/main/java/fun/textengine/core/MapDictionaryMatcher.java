package fun.textengine.core;

import fun.textengine.core.utils.ProgressTraker;
import fun.textengine.dictionaries.MapDictionary;

import java.util.HashMap;
import java.util.Map;

public class MapDictionaryMatcher implements ConceptMatcher {

    private ProgressTraker progressTraker;

    @Override
    public Map<ConceptObject, Integer> getMatches(String text) {
        Map<ConceptObject, Integer> matched = new HashMap<>();
        MapDictionary mapDictionary = MapDictionary.getInstance();
        if (progressTraker != null) {
            int allConcepts = mapDictionary.getComplexConeptsDictionary().size()
                    + mapDictionary.getSimpleConceptsDictionary().size();
            double percent = 1.00 / (double) allConcepts;
            progressTraker.setProgressPointWeight(percent);
        }
        for (Map.Entry<String, ConceptObject> entry : mapDictionary.getComplexConeptsDictionary()
                .entrySet()) {
            while (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replaceFirst(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                if (!matched.containsKey(entry.getValue())) {
                    matched.put(entry.getValue(), 1);
                } else {
                    int count = matched.get(entry.getValue()) + 1;
                    matched.put(entry.getValue(), count);
                }
            }
            if (progressTraker != null) {
                progressTraker.addProgressPoints(1);
            }
        }
        for (Map.Entry<String, ConceptObject> entry : mapDictionary.getSimpleConceptsDictionary()
                .entrySet()) {
            while (text.contains(" " + entry.getValue().getText().toLowerCase() + " ")) {
                text = text.replaceFirst(" " + entry.getValue().getText().toLowerCase() + " ", " ");
                if (!matched.containsKey(entry.getValue())) {
                    matched.put(entry.getValue(), 1);
                } else {
                    int count = matched.get(entry.getValue()) + 1;
                    matched.put(entry.getValue(), count);
                }
            }
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
}
