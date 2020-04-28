package com.pallycon.cpix.dto;

import com.pallycon.cpix.mapper.CpixNamespaceMapper;

import javax.xml.bind.annotation.XmlElement;

public class SecretDTO {
    private String plainValue;

    @XmlElement(name="PlainValue", namespace= CpixNamespaceMapper.PSKC_URI)
    public String getPlainValue() {
        return plainValue;
    }

    public void setPlainValue(String plainValue) {
        this.plainValue = plainValue;
    }


}
