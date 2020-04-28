package com.pallycon.cpix.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"minChannels", "maxChannels"})
public class AudioFilterDTO {
    String maxChannels;
    String minChannels;

    public AudioFilterDTO() {}

    public AudioFilterDTO(String maxChannels, String minChannels) {
        this.maxChannels = maxChannels;
        this.minChannels = minChannels;
    }

    @XmlAttribute(name="minChannels")
    public String getMinChannels() {
        return minChannels;
    }

    public void setMinChannels(String minChannels) {
        this.minChannels = minChannels;
    }

    @XmlAttribute(name="maxChannels")
    public String getMaxChannels() {
        return maxChannels;
    }

    public void setMaxChannels(String maxChannels) {
        this.maxChannels = maxChannels;
    }
}
