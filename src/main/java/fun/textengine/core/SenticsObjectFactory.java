package fun.textengine.core;

import org.w3c.dom.NodeList;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface SenticsObjectFactory {

    SenticsObject createNewSenticsObject(NodeList senticsNodes);
}
