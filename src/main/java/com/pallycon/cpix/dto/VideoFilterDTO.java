package com.pallycon.cpix.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"minPixels", "maxPixels"})
public class VideoFilterDTO {
    String minPixels;
    String maxPixels;

    public VideoFilterDTO() {}

    public VideoFilterDTO(String minPixels, String maxPixels) {
        this.minPixels = minPixels;
        this.maxPixels = maxPixels;
    }

    @XmlAttribute(name="minPixels")
    public String getMinPixels() {
        return minPixels;
    }

    public void setMinPixels(String minPixels) {
        this.minPixels = minPixels;
    }

    @XmlAttribute(name="maxPixels")
    public String getMaxPixels() {
        return maxPixels;
    }

    public void setMaxPixels(String maxPixels) {
        this.maxPixels = maxPixels;
    }
}
