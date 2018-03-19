package fun.textengine.importers;

import fun.textengine.core.PolarityObject;
import org.w3c.dom.NodeList;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface RdfPolarityObjectFactory {

    PolarityObject createNewPolarityObject(NodeList polarityNodes);
}
