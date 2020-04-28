package com.pallycon.cpix.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class HLSSignalingDataDTO {

    String playlist;
    String value;

    @XmlAttribute(name="playlist")
    public String getPlaylist() {
        return playlist;
    }
    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    @XmlValue
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


}
