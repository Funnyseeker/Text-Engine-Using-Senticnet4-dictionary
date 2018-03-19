package fun.textengine.dictionaries;

import fun.textengine.core.ConceptObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by somz on 19.05.2017.
 */
public class MapDictionary implements Dictionary {
    private static final String conseptKeyTemplate = "http://sentic.net/api/en/concept/";
    private static MapDictionary instance = new MapDictionary();
    private Map<String, ConceptObject> complexConceptsDictionary = new HashMap<>();
    private Map<String, ConceptObject> simpleConceptsDictionary = new HashMap<>();

    private MapDictionary() {
    }

    public static MapDictionary getInstance() {
        return instance;
    }

    public ConceptObject findByConceptName(String conceptName) {
        String key = conseptKeyTemplate + conceptName;
        if (key.contains("_")) {
            if (!complexConceptsDictionary.containsKey(key)) {
                return null;
            }
            return complexConceptsDictionary.get(key);
        } else if (!simpleConceptsDictionary.containsKey(key)) {
            return null;
        }
        return simpleConceptsDictionary.get(key);
    }

    public void addConceptObject(ConceptObject conceptObject) throws Exception {
        if (containsKey(conceptObject.getConcept())) {
            throw new Exception(
                    conceptObject.getConcept() + " - this key already presented in ConceptsDictionary");
        }
        if (conceptObject.getConceptType().equals(ConceptObject.ConceptType.COMPLEX)) {
            complexConceptsDictionary.put(conceptObject.getConcept(), conceptObject);
            return;
        }
        simpleConceptsDictionary.put(conceptObject.getConcept(), conceptObject);
    }

    public boolean containsKey(String conseptKey) {
        if (conseptKey.contains("_")) {
            return complexConceptsDictionary.containsKey(conseptKey);
        }
        return simpleConceptsDictionary.containsKey(conseptKey);
    }

    public boolean hasConcept(String conceptName) {
        String key = conseptKeyTemplate + conceptName;
        return simpleConceptsDictionary.containsKey(key) || complexConceptsDictionary.containsKey(key);
    }

    public Map<String, ConceptObject> getComplexConeptsDictionary() {
        return complexConceptsDictionary;
    }

    public Map<String, ConceptObject> getSimpleConceptsDictionary() {
        return simpleConceptsDictionary;
    }
}
