package com.pallycon.cpix;

import com.pallycon.cpix.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Brown on 2019-02-11.
 */
public class CpixBuilder {
    private final static String PREFIX_KEY_PERIOD_ID = "keyPeriod_";

    private String kId;
    private String id = null;
    private boolean keyRotation = false;
    private List<ContentKeyDTO> contentKeyList;
    private List<DrmSystemDTO> drmSystemList;

    private List<VideoFilterDTO> videoFilterList;
    private List<AudioFilterDTO> audioFilterList;
    private List pallyConTrackList;


    private long periodIndex = 0L;

    private boolean widevine_V2;
    private boolean playReady_V2;
    private boolean fairPlay_V2;
    private boolean hlsAes128_V2;


    public CpixBuilder(){
        this.contentKeyList = new ArrayList<>();
        this.drmSystemList = new ArrayList<>();
        this.videoFilterList = new ArrayList<>();
        this.audioFilterList = new ArrayList<>();
        this.pallyConTrackList = new ArrayList<>();

        this.kId = UUID.randomUUID().toString();
        this.contentKeyList.add(new ContentKeyDTO(this.kId));

        widevine_V2 = false;
        playReady_V2 = false;
        fairPlay_V2 = false;
        hlsAes128_V2 = false;
    }
    public CpixBuilder addContentKey(ContentKeyDTO contentKeyDTO) {
        this.contentKeyList.add(contentKeyDTO);
        return this;
    }
    public CpixBuilder addDrmSystemList(DrmSystemDTO drmSystemDTO){
        this.drmSystemList.add(drmSystemDTO);
        return this;
    }

    public CpixBuilder setKeyRotation(boolean keyrotation) {
        this.keyRotation = keyrotation;
        return this;
    }

    public CpixBuilder setPeriodIndex(Long index){
        this.periodIndex= index;
        return this;
    }

    public CpixBuilder setWidevine() {
        DrmSystemDTO drmSystemDTO = new DrmSystemDTO(this.kId, DRMSystemId.WIDEVINE);
        this.drmSystemList.add(drmSystemDTO);
        return this;
    }

    public CpixBuilder setPlayReady() {
        DrmSystemDTO drmSystemDTO = new DrmSystemDTO(this.kId, DRMSystemId.PLAYREADY);
        this.drmSystemList.add(drmSystemDTO);
        return this;
    }

    public CpixBuilder setFairPlay() {
        DrmSystemDTO drmSystemDTO = new DrmSystemDTO(this.kId, DRMSystemId.FAIRPLAY);
        this.drmSystemList.add(drmSystemDTO);
        return this;
    }

    public CpixBuilder setHlsAes128(){
        DrmSystemDTO drmSystemDTO = new DrmSystemDTO(this.kId, DRMSystemId.HLSAES);
        this.drmSystemList.add(drmSystemDTO);
        return this;
    }


    public CpixBuilder setWidevineV2() {
        this.widevine_V2 = true;
        return this;
    }

    public CpixBuilder setPlayReadyV2() {
        this.playReady_V2 = true;
        return this;
    }

    public CpixBuilder setFairPlayV2() {
        this.fairPlay_V2 = true;
        return this;
    }

    public CpixBuilder setHlsAes128V2(){
        this.hlsAes128_V2 = true;
        return this;
    }



    public CpixBuilder setId(String id){
        this.id = id;
        return this;
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId;
    }





    public CpixBuilder setVideo(List<VideoConfig> videoConfigList) {

        // 순서 정렬
        Collections.sort(videoConfigList);

        int i=0;
        for (VideoConfig videoConfig : videoConfigList) {
            VideoFilterDTO videoFilterDTO = new VideoFilterDTO();

            // 처음 max pixels  마지막 min pixels 은 set 안함
            if ( i != 0) {
                videoFilterDTO.setMinPixels(videoConfig.minPixels);
            }
            if ( i != videoConfigList.size()-1 ) {
                videoFilterDTO.setMaxPixels(videoConfig.maxPixels);
            }
            this.videoFilterList.add(videoFilterDTO);
            this.pallyConTrackList.add(videoConfig.track);

            i++;
        }
        return this;
    }
    public List<VideoFilterDTO> getVideo() { return videoFilterList; }




    public CpixBuilder setAudio(List<AudioConfig> audioConfigList) {

        for (AudioConfig audioConfig : audioConfigList) {
            AudioFilterDTO audioFilterDTO = new AudioFilterDTO();

            this.audioFilterList.add(audioFilterDTO);
            this.pallyConTrackList.add(audioConfig.track);
        }
        return this;
    }
    public List<AudioFilterDTO> getAudio() { return audioFilterList; }


    public CpixDTO build(){
        CpixDTO cpixDTO = new CpixDTO();

        // cid
        cpixDTO.setId(this.id);

        if ( this.videoFilterList != null && this.videoFilterList.size() > 0 ){ // v2
            cpixDTO = multiBuild(cpixDTO, this.videoFilterList, this.audioFilterList);

        }else { // v1
            cpixDTO.setContentKeyList(this.contentKeyList);
            cpixDTO = oneBuild(cpixDTO);
        }

        return cpixDTO;
    }

    // v2
    public CpixDTO multiBuild(CpixDTO cpixDTO, List<VideoFilterDTO> videoFilterList, List<AudioFilterDTO>  audioFilterList){

        List<ContentKeyPeriodDTO> contentKeyPeriodList = new ArrayList<>();
        List<ContentKeyUsageRuleDTO> contentKeyUsageRuleList = new ArrayList<>();

        this.contentKeyList = new ArrayList<>();

        // video
        int i=0;
        for (VideoFilterDTO videoFilterDTO : videoFilterList) {
            this.kId = UUID.randomUUID().toString();
            this.contentKeyList.add(new ContentKeyDTO(this.kId));


                if(this.keyRotation) {
                    ContentKeyPeriodDTO contentKeyPeriodDTO = new ContentKeyPeriodDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);
                    contentKeyPeriodList.add(contentKeyPeriodDTO);
                }

                KeyPeriodFilterDTO keyPeriodFilterDTO = new KeyPeriodFilterDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);
                List<KeyPeriodFilterDTO> keyPeriodFilterDTOList = new ArrayList<>();
                keyPeriodFilterDTOList.add(keyPeriodFilterDTO);

                ContentKeyUsageRuleDTO contentKeyUsageRuleDTO = new ContentKeyUsageRuleDTO(this.kId);

                if(this.keyRotation) {
                    contentKeyUsageRuleDTO.setKeyPeriodFilter(keyPeriodFilterDTOList);
                }


                //
                List<VideoFilterDTO> videoFilterDTOTmp = new ArrayList<>();
                videoFilterDTOTmp.add(videoFilterDTO);
                contentKeyUsageRuleDTO.setVideoFilter( videoFilterDTOTmp );


                //
                contentKeyUsageRuleDTO.setIntendedTrackType((String)this.pallyConTrackList.get(i));


                contentKeyUsageRuleList.add(contentKeyUsageRuleDTO);

                if (this.widevine_V2) this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.WIDEVINE));
                if (this.playReady_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.PLAYREADY));
                if (this.fairPlay_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.FAIRPLAY));
                if (this.hlsAes128_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.HLSAES));

                i++;


        }



        // audio
        int j=i;
        for (AudioFilterDTO audioFilterDTO : audioFilterList) {
            this.kId = UUID.randomUUID().toString();
            this.contentKeyList.add(new ContentKeyDTO(this.kId));

                if(this.keyRotation) {
                    ContentKeyPeriodDTO contentKeyPeriodDTO = new ContentKeyPeriodDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);
                    contentKeyPeriodList.add(contentKeyPeriodDTO);
                }

                KeyPeriodFilterDTO keyPeriodFilterDTO = new KeyPeriodFilterDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);
                List<KeyPeriodFilterDTO> keyPeriodFilterDTOList = new ArrayList<>();
                keyPeriodFilterDTOList.add(keyPeriodFilterDTO);

                ContentKeyUsageRuleDTO contentKeyUsageRuleDTO = new ContentKeyUsageRuleDTO(this.kId);

                if(this.keyRotation) {
                    contentKeyUsageRuleDTO.setKeyPeriodFilter(keyPeriodFilterDTOList);
                }

                //
                List<AudioFilterDTO> audioFilterDTOTmp = new ArrayList<>();
                audioFilterDTOTmp.add(audioFilterDTO);
                contentKeyUsageRuleDTO.setAudioFilter( audioFilterDTOTmp );

                //
                contentKeyUsageRuleDTO.setIntendedTrackType((String)this.pallyConTrackList.get(j));


                contentKeyUsageRuleList.add(contentKeyUsageRuleDTO);

                if (this.widevine_V2) this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.WIDEVINE));
                if (this.playReady_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.PLAYREADY));
                if (this.fairPlay_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.FAIRPLAY));
                if (this.hlsAes128_V2)  this.drmSystemList.add(new DrmSystemDTO(this.kId, DRMSystemId.HLSAES));

                j++;

        }



        cpixDTO.setContentKeyList(this.contentKeyList);
        cpixDTO.setContentKeyUsageRuleList(contentKeyUsageRuleList);
        cpixDTO.setDrmSystemList(this.drmSystemList);

        if(this.keyRotation) {
            cpixDTO.setContentKeyPeriodList(contentKeyPeriodList);
        }

        if(this.periodIndex > 0 ){
            contentKeyPeriodList.stream()
                    .filter( keyPeriodDTO -> keyPeriodDTO.getId().equals(this.PREFIX_KEY_PERIOD_ID+this.kId))
                    .findFirst()
                    .ifPresent(
                            keyPeriodDTO -> keyPeriodDTO.setIndex(Long.toString(periodIndex)
                            )
                    );
        }

        return cpixDTO;
    }









    // v1
    public CpixDTO oneBuild(CpixDTO cpixDTO){

        cpixDTO.setDrmSystemList(this.drmSystemList);

        if(this.keyRotation){

            cpixDTO.setContentKeyList(this.contentKeyList);

            List<ContentKeyPeriodDTO> contentKeyPeriodList = new ArrayList<>();
            ContentKeyPeriodDTO contentKeyPeriodDTO = new ContentKeyPeriodDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);
            contentKeyPeriodList.add(contentKeyPeriodDTO);

            List<ContentKeyUsageRuleDTO> contentKeyUsageRuleList = new ArrayList<>();


            ContentKeyUsageRuleDTO contentKeyUsageRuleDTO = new ContentKeyUsageRuleDTO(this.kId);
            List<KeyPeriodFilterDTO> keyPeriodFilterDTOList = new ArrayList<>();
            KeyPeriodFilterDTO keyPeriodFilterDTO = new KeyPeriodFilterDTO(this.PREFIX_KEY_PERIOD_ID + this.kId);

            keyPeriodFilterDTOList.add(keyPeriodFilterDTO);
            contentKeyUsageRuleDTO.setKeyPeriodFilter(keyPeriodFilterDTOList);
            contentKeyUsageRuleList.add(contentKeyUsageRuleDTO);

            cpixDTO.setContentKeyPeriodList(contentKeyPeriodList);
            cpixDTO.setContentKeyUsageRuleList(contentKeyUsageRuleList);

            if(this.periodIndex > 0 ){
                contentKeyPeriodList.stream()
                        .filter( keyPeriodDTO -> keyPeriodDTO.getId().equals(this.PREFIX_KEY_PERIOD_ID+this.kId))
                        .findFirst()
                        .ifPresent(
                                keyPeriodDTO -> keyPeriodDTO.setIndex(Long.toString(periodIndex)
                                )
                        );
            }
        }

        return cpixDTO;
    }




}
