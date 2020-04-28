package com.pallycon.cpix.dto;

import com.pallycon.cpix.mapper.CpixNamespaceMapper;
import org.apache.commons.codec.binary.Base64;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DrmSystemDTO {
    String systemId;
    String kid;
    String pssh;
    String uriExtXKey;
    String contentProtectionData;
    String hlsSignalingData;
    HLSSignalingDataDTO hlsSignalingDataDTOMaster;
    HLSSignalingDataDTO hlsSignalingDataDTOMedia;

    public DrmSystemDTO(){}
    public DrmSystemDTO(String kid, String systemId) {
        this.systemId = systemId;
        this.kid = kid;
    }

    @XmlAttribute(name="systemId")
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @XmlAttribute(name="kid")
    public String getKid() {
        return kid;
    }
    public void setKid(String kid) {
        this.kid = kid;
    }

    @XmlElement(name="PSSH", namespace=CpixNamespaceMapper.CPIX_URI)
    public String getPssh() {
        return pssh;
    }
    public void setPssh(String pssh) {
        this.pssh = pssh;
    }

    @XmlElement(name="URIExtXKey", namespace=CpixNamespaceMapper.CPIX_URI)
    public String getUriExtXKey() {
        return uriExtXKey;
    }
    public void setUriExtXKey(String uriExtXKey) {
        this.uriExtXKey = uriExtXKey;
    }

    @XmlElement(name="ContentProtectionData", namespace=CpixNamespaceMapper.CPIX_URI)
    public String getContentProtectionData() {
        return contentProtectionData;
    }
    public void setContentProtectionData(String contentProtectionData) {
        this.contentProtectionData = contentProtectionData;
    }


    // String
    @XmlElement(name="HLSSignalingData", namespace="urn:dashif:org:cpix")
    public String getHlsSignalingData() {
        return hlsSignalingData;
    }
    public void setHlsSignalingData(String hlsSignalingData) {
        this.hlsSignalingData = hlsSignalingData;
    }

    // master
    @XmlElement(name="HLSSignalingData", type=HLSSignalingDataDTO.class ,  namespace="urn:dashif:org:cpix" )
    public HLSSignalingDataDTO getHlsSignalingDataPalyListMaster() {
        return hlsSignalingDataDTOMaster;
    }
    public void setHlsSignalingDataPalyListMaster(HLSSignalingDataDTO hlsSignalingDataDTOMaster) {
        this.hlsSignalingDataDTOMaster = hlsSignalingDataDTOMaster;
    }

    //media
    @XmlElement(name="HLSSignalingData", type=HLSSignalingDataDTO.class ,  namespace="urn:dashif:org:cpix" )
    public HLSSignalingDataDTO getHlsSignalingDataPalyListMedia() {
        return hlsSignalingDataDTOMedia;
    }
    public void setHlsSignalingDataPalyListMedia(HLSSignalingDataDTO hlsSignalingDataDTOMedia) {
        this.hlsSignalingDataDTOMedia = hlsSignalingDataDTOMedia;
    }


    public String getFairPlayDrmUri(String hlsSignalingData) {
        String fairPlayDrmUri = null;
        if( hlsSignalingData != null && !"".equals(hlsSignalingData)){
            try {
                String setHLSSignalingData = new String(Base64.decodeBase64(hlsSignalingData));
                fairPlayDrmUri = setHLSSignalingData.split(",")[1].replaceAll("URI=", "");
            }catch (Exception e){
                fairPlayDrmUri = null;
            }
        }
        return fairPlayDrmUri;
    }
}
