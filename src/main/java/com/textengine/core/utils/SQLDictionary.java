package com.textengine.core.utils;

import com.textengine.core.ConceptObject;
import com.textengine.core.Polarity;
import com.textengine.core.PolarityObject;
import com.textengine.core.SenticsObject;
import com.textengine.core.impl.ConceptObjectImpl;
import com.textengine.core.impl.PolarityObjectImpl;
import com.textengine.core.impl.SenticsObjectImpl;

import java.sql.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Funnyseeker on 24.05.2017.
 */
public class SQLDictionary {
    private static SQLDictionary instance = new SQLDictionary();
    private final String conseptKeyTemplate = "http://sentic.net/api/en/concept/";
    private final String selectComplexPattern = "select * from [Dictionary] where ([Concept] LIKE ''% {0}'' OR ''{0} %'') AND COMPLEX = 1";
    private final String selectSimplePattern = "select * from [Dictionary] where ([Concept] LIKE ''{0}'') AND COMPLEX = 0";
    private Connection conn = null;
    private final String clazzName = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:database.s3db";
    public Map<ConceptObject, Integer> matched = new HashMap<>();

    private SQLDictionary() {
        try {
            Class.forName(clazzName);
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static SQLDictionary getInstance() {
        return instance;
    }

    public Map<ConceptObject, Integer> getConceptObjects(String text) {

        matched = new HashMap<>();

        String[] words = text.split("[,;:.!?\\s]+");
        for (String word : words) {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(MessageFormat.format(selectComplexPattern, word));
                while (resultSet.next()) {
                    text = check(resultSet, text, matched);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (String word : words) {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(MessageFormat.format(selectSimplePattern, word));
                while (resultSet.next()) {
                    text = check(resultSet, text, matched);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return matched;
    }

    public void createSQLDict() {
        String sql = "insert into [Dictionary] (Concept,PolarityIntensity,SenticsPleasantness,SenticsAttention," +
                "SenticsSensitivity, SenticsAptitude, Complex) values (?,?,?,?,?,?,?)";
        int counter = 0;
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getComplexConeptsDictionary().entrySet()) {
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, entry.getValue().getText());
                statement.setFloat(2, entry.getValue().getConceptPolarity().getIntensity());
                statement.setFloat(3, entry.getValue().getSentics().getPleasantness());
                statement.setFloat(4, entry.getValue().getSentics().getAttention());
                statement.setFloat(5, entry.getValue().getSentics().getSensitivity());
                statement.setFloat(6, entry.getValue().getSentics().getAptitude());
                statement.setBoolean(7, true);
                statement.execute();
                counter++;
                System.out.println(counter);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getSimpleConceptsDictionary().entrySet()) {
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, entry.getValue().getText());
                statement.setFloat(2, entry.getValue().getConceptPolarity().getIntensity());
                statement.setFloat(3, entry.getValue().getSentics().getPleasantness());
                statement.setFloat(4, entry.getValue().getSentics().getAttention());
                statement.setFloat(5, entry.getValue().getSentics().getSensitivity());
                statement.setFloat(6, entry.getValue().getSentics().getAptitude());
                statement.setBoolean(7, false);
                statement.execute();
                counter++;
                System.out.println(counter);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String check(ResultSet resultSet, String text, Map<ConceptObject, Integer> matched) throws SQLException {
        String result = resultSet.getString("Concept");
        int counter = 0;
        boolean isContains = false;
        while (text.contains(" " + result.toLowerCase() + " ")) {
            text = text.replaceFirst(" " + result.toLowerCase() + " ", " | ");
            counter++;
            isContains = true;
        }
        if (isContains) {
            SenticsObject senticsObject = new SenticsObjectImpl(
                    resultSet.getFloat("SenticsPleasantness"),
                    resultSet.getFloat("SenticsAttention"),
                    resultSet.getFloat("SenticsSensitivity"),
                    resultSet.getFloat("SenticsAptitude"));
            PolarityObject polarityObject = new PolarityObjectImpl(
                    Polarity.getFromIntesity(resultSet.getFloat("PolarityIntensity")),
                    resultSet.getFloat("PolarityIntensity"));
            ConceptObject conceptObject = new ConceptObjectImpl(
                    conseptKeyTemplate + result.replace(" ", "_"),
                    null,
                    null,
                    senticsObject,
                    polarityObject);
            matched.put(conceptObject, counter);
        }
        return text;
    }
}
