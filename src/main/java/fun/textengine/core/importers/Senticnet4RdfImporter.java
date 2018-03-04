package fun.textengine.core.importers;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;
import fun.textengine.core.dictionaries.MapDictionary;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class Senticnet4RdfImporter {

    public static MapDictionary parse(URL url) throws Exception {
        System.out.println("Starting dictionary loading...");
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = documentBuilder.parse(url.toString());
        Node root = document.getDocumentElement();
//        print(root);
        createMapDictionary(root);
        System.out.println("Finished dictionary loading...");
        return MapDictionary.getInstance();
    }

    private static void createMapDictionary(Node root) throws Exception {
        NodeList childs = root.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node currNode = childs.item(i);
            if (currNode.getNodeName().equals("rdf:Description")) {
                RdfObjectsFactory factory = RdfObjectsFactoryImpl.getInstance();
                SenticsObject senticsObject = factory.createNewSenticsObject(getChildsNodes(currNode, "sentics"));
                PolarityObject polarityObject = factory.createNewPolarityObject(getChildsNodes(currNode, "polarity"));
                ConceptObject conceptObject = factory.createNewConceptObject(currNode, senticsObject, polarityObject);
                MapDictionary.getInstance().addConceptObject(conceptObject);
            }
        }
    }

    private static NodeList getChildsNodes(Node root, String tagName) {
        NodeList childs = root.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            if (childs.item(i).getNodeName().equals(tagName)) {
                return childs.item(i).getChildNodes();
            }
        }
        return null;
    }

    @Deprecated
    private void print(Node root) throws InvocationTargetException, IllegalAccessException {
        NodeList childs = root.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node currNode = childs.item(i);
            if (currNode.getNodeName().equals("rdf:Description")) {
                NamedNodeMap attributes = currNode.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node node = attributes.item(j);
                    printMethods(node);
                }
            }
        }
    }

    @Deprecated
    private void printMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getParameters().length == 0 && method.getReturnType().equals(String.class)) {
                System.out.println(method.getName() + ": " + method.invoke(object));
            }
        }
    }
}
