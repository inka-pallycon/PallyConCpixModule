package com.pallycon.cpix.dto;

import com.pallycon.cpix.mapper.CpixNamespaceMapper;

import javax.xml.bind.annotation.XmlElement;

public class DataDTO {
    private SecretDTO secret;

    @XmlElement(name="Secret", namespace=CpixNamespaceMapper.PSKC_URI)
    public SecretDTO getSecret() {
        return secret;
    }

    public void setSecret(SecretDTO secret) {
        this.secret = secret;
    }
}
