package com.textengine.core;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * Created by Funnyseeker on 19.05.2017.
 */
public interface SenticsObjectFactory {

    SenticsObject createNewSenticsObject(NodeList senticsNodes);
}
