package com.pallycon.cpix.dto;


import com.pallycon.cpix.mapper.CpixNamespaceMapper;
import com.pallycon.cpix.util.Base64Encoder;
import com.pallycon.cpix.util.StringUtil;

import javax.xml.bind.annotation.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="CPIX", namespace=CpixNamespaceMapper.CPIX_URI)
public class CpixDTO {
    private String id;
    private List<ContentKeyDTO> contentKeyList;
    private List<DrmSystemDTO> drmSystemList;
    private List<ContentKeyPeriodDTO> contentKeyPeriodList;
    private List<ContentKeyUsageRuleDTO> contentKeyUsageRuleList;

    @XmlAttribute(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElementWrapper(name="ContentKeyList", namespace=CpixNamespaceMapper.CPIX_URI)
    @XmlElement(name="ContentKey", type=ContentKeyDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<ContentKeyDTO> getContentKeyList() {
        return contentKeyList;
    }

    public void setContentKeyList(List<ContentKeyDTO> contentKeyList) {
        this.contentKeyList = contentKeyList;
    }

    @XmlElementWrapper(name="DRMSystemList", namespace=CpixNamespaceMapper.CPIX_URI)
    @XmlElement(name="DRMSystem", type=DrmSystemDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<DrmSystemDTO> getDrmSystemList() {
        return drmSystemList;
    }

    public void setDrmSystemList(List<DrmSystemDTO> drmSystemList) {
        this.drmSystemList = drmSystemList;
    }

    @XmlElementWrapper(name="ContentKeyPeriodList", namespace=CpixNamespaceMapper.CPIX_URI)
    @XmlElement(name="ContentKeyPeriod", type=ContentKeyPeriodDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<ContentKeyPeriodDTO> getContentKeyPeriodList() {
        return contentKeyPeriodList;
    }

    public void setContentKeyPeriodList(List<ContentKeyPeriodDTO> contentKeyPeriodList) {
        this.contentKeyPeriodList = contentKeyPeriodList;
    }

    @XmlElementWrapper(name="ContentKeyUsageRuleList", namespace=CpixNamespaceMapper.CPIX_URI)
    @XmlElement(name="ContentKeyUsageRule", type=ContentKeyUsageRuleDTO.class, namespace=CpixNamespaceMapper.CPIX_URI)
    public List<ContentKeyUsageRuleDTO> getContentKeyUsageRuleList() {
        return contentKeyUsageRuleList;
    }

    public void setContentKeyUsageRuleList(List<ContentKeyUsageRuleDTO> contentKeyUsageRuleList) {
        this.contentKeyUsageRuleList = contentKeyUsageRuleList;
    }


    public String getContentKey(){
        return this.contentKeyList.stream().findFirst().get().getData().getSecret().getPlainValue();
    }

    public String getContentKeyToHex() throws Exception{
        return StringUtil.byteArrayToHex(Base64Encoder.decode(getContentKey()));
    }

    public String getContentKeyId() {
        return Base64Encoder.encode(StringUtil.hexToByteArray(getContentKeyIdToHex()));
    }

    public String getContentKeyIdToHex(){
        return this.contentKeyList.stream().findFirst().get().getKid().replaceAll("-", "");
    }

    public String getContentIv(){
        return this.contentKeyList.stream().findFirst().get().getExplicitIV();
    }

    public String getContentIvToHex() throws Exception{
        return StringUtil.byteArrayToHex(Base64Encoder.decode(getContentIv()));
    }

    public String getPssh(String drmSystemId){
        return this.drmSystemList.stream()
                .filter(drmSystemDTO -> drmSystemDTO.getSystemId().equals(drmSystemId))
                .findFirst().get().getPssh();
    }
    public String getBitmovinPssh(String drmSystemId){
        byte[] fullPssh = Base64.getDecoder().decode(this.drmSystemList.stream()
                .filter(drmSystemDTO -> drmSystemDTO.getSystemId().equals(drmSystemId))
                .findFirst().get().getPssh());
        int bitmovinPsshLength = fullPssh.length - 32;
        byte[] bitmovinPssh = new byte[bitmovinPsshLength];
        System.arraycopy(fullPssh, 32, bitmovinPssh, 0, bitmovinPsshLength);

        return Base64.getEncoder().encodeToString(bitmovinPssh);
    }


    public String getBitmovinPsshV2(String pssh){

        byte[] fullPssh = Base64.getDecoder().decode(pssh);
        int bitmovinPsshLength = fullPssh.length - 32;
        byte[] bitmovinPssh = new byte[bitmovinPsshLength];
        System.arraycopy(fullPssh, 32, bitmovinPssh, 0, bitmovinPsshLength);

        return Base64.getEncoder().encodeToString(bitmovinPssh);
    }

    public String getFairPlayUrl() throws Exception{
        return new String(Base64Encoder.decode(this.drmSystemList.stream()
                .filter(drmSystemDTO -> drmSystemDTO.getSystemId().equals(DRMSystemId.FAIRPLAY))
                .findFirst().get().getUriExtXKey()));
    }


    public ContentKeyUsageRuleDTO getContentKeyUsageRuleDTO(String track) {
        return this.contentKeyUsageRuleList.stream().collect(Collectors.groupingBy(el -> track.equals(el.getIntendedTrackType()))).get(true).stream().findFirst().get();
    }


    public DrmSystemDTO getDrmSystemDTO(String systemId , String kid){
        return this.drmSystemList.stream().collect(Collectors.groupingBy(el -> systemId.equals(el.getSystemId()) && kid.equals(el.getKid()) )).get(true).stream().findFirst().get();
    }


    public ContentKeyDTO getContentKeyDTO(String kid) {
        return this.contentKeyList.stream().collect(Collectors.groupingBy(el -> kid.equals(el.getKid()))).get(true).stream().findFirst().get();
    }



}
