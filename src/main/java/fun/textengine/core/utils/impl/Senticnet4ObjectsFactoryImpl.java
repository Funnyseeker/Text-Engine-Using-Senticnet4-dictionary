package fun.textengine.core.utils.impl;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.Polarity;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;
import fun.textengine.core.impl.ConceptObjectImpl;
import fun.textengine.core.impl.PolarityObjectImpl;
import fun.textengine.core.impl.SenticsObjectImpl;
import fun.textengine.core.utils.Senticnet4ObjectsFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public class Senticnet4ObjectsFactoryImpl implements Senticnet4ObjectsFactory {
    private static Senticnet4ObjectsFactory instance = new Senticnet4ObjectsFactoryImpl();

    private Senticnet4ObjectsFactoryImpl() {
    }

    public static Senticnet4ObjectsFactory getInstance() {
        return instance;
    }

    @Override
    public PolarityObject createNewPolarityObject(NodeList polarityNodes) {
        float intensity = -2;
        Polarity value = null;
        for (int i = 0; i < polarityNodes.getLength(); i++) {
            Node currNode = polarityNodes.item(i);
            switch (currNode.getNodeName()) {
                case "intensity":
                    intensity = Float.parseFloat(currNode.getTextContent());
                    break;
                case "value":
                    value = Polarity.getFromKey(currNode.getTextContent());
                    break;
            }
        }
        if (value == null || intensity < -1) {
            return null;
        }
        return new PolarityObjectImpl(value, intensity);
    }

    @Override
    public ConceptObject createNewConceptObject(Node node, SenticsObject senticsObject, PolarityObject polarityObject) {
        String concept = node.getAttributes().getNamedItem("rdf:about").getTextContent();
        NodeList childs = node.getChildNodes();
        List<String> semantics = new ArrayList<>();
        List<String> moodtags = new ArrayList<>();
//        for (int i = 0; i < childs.getLength(); i++) {
//            Node currNode = childs.item(i);
//            if(currNode.getNodeName().equals("semantics")){
//
//            }
//        }
        return new ConceptObjectImpl(concept, semantics, moodtags, senticsObject, polarityObject);
    }

    @Override
    public SenticsObject createNewSenticsObject(NodeList senticsNodes) {
        float pleasantness = -2;
        float attention = -2;
        float sensitivity = -2;
        float aptitude = -2;
        for (int i = 0; i < senticsNodes.getLength(); i++) {
            Node currNode = senticsNodes.item(i);
            switch (currNode.getNodeName()) {
                case "pleasantness":
                    pleasantness = Float.parseFloat(currNode.getTextContent());
                    break;
                case "attention":
                    attention = Float.parseFloat(currNode.getTextContent());
                    break;
                case "sensitivity":
                    sensitivity = Float.parseFloat(currNode.getTextContent());
                    break;
                case "aptitude":
                    aptitude = Float.parseFloat(currNode.getTextContent());
                    break;
            }
        }
        if (pleasantness < -1 || attention < -1 || sensitivity < -1 || aptitude < -1) {
            return null;
        }
        return new SenticsObjectImpl(pleasantness, attention, sensitivity, aptitude);
    }
}
