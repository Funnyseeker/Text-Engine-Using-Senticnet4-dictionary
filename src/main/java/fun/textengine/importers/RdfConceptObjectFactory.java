package fun.textengine.importers;

import fun.textengine.core.ConceptObject;
import fun.textengine.core.PolarityObject;
import fun.textengine.core.SenticsObject;
import org.w3c.dom.Node;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface RdfConceptObjectFactory {

    ConceptObject createNewConceptObject(Node node, SenticsObject senticsObject, PolarityObject polarityObject);
}
