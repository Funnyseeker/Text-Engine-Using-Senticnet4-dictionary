package fun.textengine.ui;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.TextObject;
import fun.textengine.core.utils.SQLDictionary;
import fun.textengine.core.utils.TextEngineParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try {
//            URL documentUrl = ClassLoader.getSystemClassLoader().getResource("senticnet4/senticnet4.rdf.xml");
//            Senticnet4RdfParser parser = new Senticnet4RdfParser();
//            parser.parse(documentUrl);
//            SQLDictionary.getInstance().createSQLDict();
            List<String[]> texts = new ArrayList<>();
            System.out.println("Enter file name:");
            Scanner sc = new Scanner(System.in);
            String filename = sc.nextLine();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filename), StandardCharsets.UTF_8))) {
                String line;
                List<String> currText = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    if (line.equals("///")) {
                        List<String> newText = new ArrayList<>(currText);
                        texts.add(toStrArray(newText.toArray()));
                        currText = new ArrayList<>();
                        continue;
                    }
                    currText.add(line);
                }
                texts.add(toStrArray(currText.toArray()));
            } catch (IOException e) {
                e.printStackTrace();
                System.in.read();
            }
            for (String[] textLines : texts) {
                String text = "";
                System.out.println("\ntext:");
                for (String line : textLines) {
                    System.out.println(line);
                    text += line;
                }
                TextObject textObject = new TextEngineParser().parseText(text.trim());
                System.out.println("Sentiment: " + textObject.getPolarityObject().getIntensity());
                System.out.println("Sentiment polarity: " + textObject.getPolarityObject().getPolarity().name());
                String neg = "{0} group : {1} \n {2}";
                System.out.println(MessageFormat.format(neg, "Negative",
                        textObject.getNegativeGroup().split(";").length - 1, textObject.getNegativeGroup()));
                System.out.println(MessageFormat.format(neg, "Positive",
                        textObject.getPositiveGroup().split(";").length - 1, textObject.getPositiveGroup()));
                for (Map.Entry<ConceptObject, Integer> entry : SQLDictionary.getInstance().matched.entrySet()) {
                    System.out.println(entry.getKey().getText() + " : " + entry.getValue());
                }
                System.out.println("-----------------------------------");
                System.gc();
            }
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
            System.in.read();
        }
    }

    private static String[] toStrArray(Object[] objects) {
        String[] strs = new String[objects.length];
        for (int i = 0; i < objects.length - 1; i++) {
            strs[i] = (String) objects[i];
        }
        return strs;
    }
}
