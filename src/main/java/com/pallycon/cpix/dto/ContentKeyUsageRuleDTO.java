package com.pallycon.cpix.dto;

import com.pallycon.cpix.mapper.CpixNamespaceMapper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ContentKeyUsageRuleDTO {
    private String kid;
    private String intendedTrackType;
    private List<KeyPeriodFilterDTO> keyPeriodFilter;
    private List<VideoFilterDTO> videoFilter;
    private List<AudioFilterDTO> audioFilter;


    public ContentKeyUsageRuleDTO() {
    }

    public ContentKeyUsageRuleDTO(String kid) {
        this.kid = kid;
    }

    public ContentKeyUsageRuleDTO(List<KeyPeriodFilterDTO> keyPeriodFilter, List<VideoFilterDTO> videoFilter, List<AudioFilterDTO> audioFilter) {
        this.keyPeriodFilter = keyPeriodFilter;
        this.videoFilter = videoFilter;
        this.audioFilter = audioFilter;
    }

    @XmlAttribute(name="kid")
    public String getKid() {
        return kid;
    }
    public void setKid(String kid) {
        this.kid = kid;
    }

    @XmlAttribute(name="intendedTrackType")
    public String getIntendedTrackType() {
        return intendedTrackType;
    }
    public void setIntendedTrackType(String intendedTrackType) {
        this.intendedTrackType = intendedTrackType;
    }


    @XmlElement(name="KeyPeriodFilter", type=KeyPeriodFilterDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<KeyPeriodFilterDTO> getKeyPeriodFilter() {
        return keyPeriodFilter;
    }
    public void setKeyPeriodFilter(List<KeyPeriodFilterDTO> keyPeriodFilter) {
        this.keyPeriodFilter = keyPeriodFilter;
    }


    @XmlElement(name="VideoFilter", type=VideoFilterDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<VideoFilterDTO> getVideoFilter() {
        return videoFilter;
    }
    public void setVideoFilter(List<VideoFilterDTO> videoFilter) {
        this.videoFilter = videoFilter;
    }


    @XmlElement(name="AudioFilter", type=AudioFilterDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<AudioFilterDTO> getAudioFilter() {
        return audioFilter;
    }
    public void setAudioFilter(List<AudioFilterDTO> audioFilter) {
        this.audioFilter = audioFilter;
    }

}
