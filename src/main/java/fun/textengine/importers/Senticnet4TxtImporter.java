package fun.textengine.importers;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.Polarity;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;
import fun.textengine.core.impl.ConceptObjectImpl;
import fun.textengine.core.impl.PolarityObjectImpl;
import fun.textengine.core.impl.SenticsObjectImpl;
import fun.textengine.dictionaries.MapDictionary;
import fun.textengine.dictionaries.SQLDictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Funnyseeker on 18.03.2018
 */
public class Senticnet4TxtImporter {
    private static final String conseptKeyTemplate = "http://sentic.net/api/de/concept/";

    public static MapDictionary parse(URL url) throws Exception {
        System.out.println("Starting dictionary loading...");
        BufferedReader bufRead = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(url.getPath()), "UTF-8"));
        String myLine = null;
        while ((myLine = bufRead.readLine()) != null) {
            myLine = myLine.replaceAll("\"|senticnet", "");
            MapDictionary.getInstance().addConceptObject(getConcept(myLine.split("=")));
        }
        System.out.println("Finished dictionary loading...");
        return MapDictionary.getInstance();
    }


    private static ConceptObject getConcept(String[] inputArray) {
        String concept = inputArray[0].replaceAll("\\[|\\]", "");
        concept = conseptKeyTemplate + concept;
        String[] infoParts = inputArray[1].replaceAll("\\[|\\]", "").split(",");
        SenticsObject senticsObject = getSentic(infoParts);
        PolarityObject polarityObject = getPolarity(infoParts);
        return new ConceptObjectImpl(concept, null, null, senticsObject, polarityObject);
    }

    private static SenticsObject getSentic(String[] infoParts) {
        return new SenticsObjectImpl(Float.parseFloat(infoParts[0]), Float.parseFloat(infoParts[1]),
                                     Float.parseFloat(infoParts[2]), Float.parseFloat(infoParts[3]));
    }

    private static PolarityObject getPolarity(String[] infoParts) {
        boolean prevMoodTag = false;
        float intensity = 0;
        for (String infoPart : infoParts) {
            if (infoPart.contains("#")) {
                prevMoodTag = true;
                continue;
            } else if (prevMoodTag) {
                intensity = Float.parseFloat(infoPart);
                break;
            }
        }
        return new PolarityObjectImpl(Polarity.getFromIntesity(intensity), intensity);
    }

    public static void main(String[] args) throws Exception {
        URL documentUrl = ClassLoader.getSystemClassLoader().getResource("senticnet4/data_de.txt");
        parse(documentUrl);
        SQLDictionary.getInstance().setCurrDictTableCode(1);
        SQLDictionary.getInstance().createSQLDict();
    }
}
