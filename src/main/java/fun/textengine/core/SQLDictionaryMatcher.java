package fun.textengine.core;


import fun.textengine.core.utils.SQLDictionary;

import java.util.Map;

public class SQLDictionaryMatcher implements ConceptMatcher {
    @Override
    public Map<ConceptObject, Integer> getMatches(String text) {
        return SQLDictionary.getInstance().getConceptObjects(text);
    }
}
