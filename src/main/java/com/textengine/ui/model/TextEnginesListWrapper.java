package com.textengine.ui.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by Funnyseeker on 23.05.2017.
 */
@XmlRootElement(name = "texts")
@XmlSeeAlso(TextEngineInfo.class)
public class TextEnginesListWrapper {
    private List infos;

    @XmlElement(name = "TextEngineInfo")
    public List getInfos() {
        return infos;
    }

    public void setInfos(List infos) {
        this.infos = infos;
    }
}
