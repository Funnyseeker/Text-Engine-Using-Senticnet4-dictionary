package fun.textengine.dictionaries;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.Polarity;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;
import fun.textengine.core.impl.ConceptObjectImpl;
import fun.textengine.core.impl.PolarityObjectImpl;
import fun.textengine.core.impl.SenticsObjectImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Funnyseeker on 24.05.2017.
 */
public class SQLDictionary implements Dictionary {
    private static SQLDictionary instance = new SQLDictionary();
    private final String conseptKeyTemplate = "http://sentic.net/api/en/concept/";
    private final String selectComplexPattern = "select * from [Dictionary] where COMPLEX = 1 AND ([Concept] LIKE ''%{0}%'')";
    private final String selectSimplePattern = "select * from [Dictionary] where COMPLEX = 0 AND ([Concept] LIKE ''{0}'')";
    private final String clazzName = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:database.s3db";
    private Connection conn = null;


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

    public List<ConceptObject> getComplexConceptObjects(String word) {
        List<ConceptObject> conceptObjectList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(MessageFormat.format(selectComplexPattern, word));
            while (resultSet.next()) {
                ConceptObject conceptObject = conceptObjectFromResultSet(resultSet);
                conceptObjectList.add(conceptObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conceptObjectList;
    }

    public List<ConceptObject> getConceptObjects(String word) {
        List<ConceptObject> conceptObjectList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(MessageFormat.format(selectSimplePattern, word));
            while (resultSet.next()) {
                ConceptObject conceptObject = conceptObjectFromResultSet(resultSet);
                conceptObjectList.add(conceptObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conceptObjectList;
    }

    public void createSQLDict() {
        String sql = "insert into [Dictionary] (Concept,PolarityIntensity,SenticsPleasantness,SenticsAttention," +
                "SenticsSensitivity, SenticsAptitude, Complex) values (?,?,?,?,?,?,?)";
        int counter = 0;
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getComplexConeptsDictionary()
                .entrySet()) {
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
        for (Map.Entry<String, ConceptObject> entry : MapDictionary.getInstance().getSimpleConceptsDictionary()
                .entrySet()) {
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

    private ConceptObject conceptObjectFromResultSet(ResultSet resultSet) {
        try {
            SenticsObject senticsObject = null;
            String result = resultSet.getString("Concept");
            senticsObject = new SenticsObjectImpl(
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
            return conceptObject;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
