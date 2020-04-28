package com.pallycon.cpix;

import com.pallycon.cpix.dto.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Brown on 2019-10-23.
 */
public class CPixModuleImplTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ENC_TOKEN = "eyJhY2Nlc3Nfa2V5IjoiZHNJb2xjN2gxRzhUVW1JMTdiWXd4aFV1TkZvRmNlNzJjeDllTU9rNjJ3YjhWTjJQZGdwV1lISXhTRVg5ZjBIaSIsInNpdGVfaWQiOiJERU1PIn0=";
    private String CONTENT_ID = "palmulti";

    @Test
    public void getDashKeyInfo() throws Exception {
        CPixCommonModule cPixCommonModule = new CPixCommonModule();
        CpixDTO cpixDTO = cPixCommonModule.getDashKeyInfo(ENC_TOKEN, CONTENT_ID);

        logger.info(cpixDTO.getPssh(DRMSystemId.WIDEVINE));
        logger.info(cpixDTO.getContentKeyIdToHex());
    }

    @Test
    public void getDashKeyInfo2() throws Exception {
        CPixCommonModule cPixCommonModule = new CPixCommonModule();
        CpixDTO cpixDTO = cPixCommonModule.getDashKeyInfo(ENC_TOKEN, CONTENT_ID, true, 5);

        logger.info(cpixDTO.getPssh(DRMSystemId.WIDEVINE));
        logger.info(cpixDTO.getContentKeyIdToHex());
    }
    @Test
    public void getDashKeyInfo3() throws Exception {
        CPixCommonModule cPixCommonModule = new CPixCommonModule();
        CpixDTO cpixDTO = cPixCommonModule.getDashKeyInfo(ENC_TOKEN, CONTENT_ID);

        logger.info(cpixDTO.getPssh(DRMSystemId.WIDEVINE));
        logger.info(cpixDTO.getContentKeyIdToHex());
        logger.info(cpixDTO.getPssh(DRMSystemId.PLAYREADY));
        logger.info(cpixDTO.getBitmovinPssh(DRMSystemId.WIDEVINE));
        logger.info(cpixDTO.getBitmovinPssh(DRMSystemId.PLAYREADY));
    }



    @Test
    public void getDashKeyInfoMultiKey() throws Exception {

        // bitmovin API 쪽 소스
        List<VideoConfig> videoProfile = Arrays.asList(
                new VideoConfig(480) // 480p
                , new VideoConfig(720) // 720p
                , new VideoConfig(1080) // 1080p
                , new VideoConfig(2160) // 4K
                , new VideoConfig(4320) // 8K
        );

        List<VideoConfig> videoProfile2 = Arrays.asList(
                new VideoConfig(480) // 480p
                , new VideoConfig(VideoConfig.Track.SD ,720) // 720p
                , new VideoConfig(1080) // 1080p
                , new VideoConfig(VideoConfig.Track.HD ,2160) // 4K
                , new VideoConfig(VideoConfig.Track.UHD2 ,4320) // 8K
        );

        List<AudioConfig> audioProfile =
                Collections.singletonList(new AudioConfig());


        PallyConConfig pallyConConfig = new PallyConConfig();
        pallyConConfig.setVideoConfigList(videoProfile);
        pallyConConfig.setAudioConfigList(audioProfile);

        CpixModule cpixModule = new CPixCommonModule();
        CpixDTO cpixDTO = cpixModule.getDashKeyInfo(ENC_TOKEN, CONTENT_ID, pallyConConfig);
//        CpixDTO cpixDTO = cpixModule.getDashKeyInfo(ENC_TOKEN, CONTENT_ID, pallyConConfig, true, 5);

    }


    @Test
    public void getHlsKeyInfoMultiKey() throws Exception {

        // bitmovin API 쪽 소스
        List<VideoConfig> videoProfile = Arrays.asList(
                new VideoConfig(480) // 480p
                , new VideoConfig(720) // 720p
                , new VideoConfig(1080) // 1080p
                , new VideoConfig(2160) // 4K
                , new VideoConfig(4320) // 8K
        );

        List<VideoConfig> videoProfile2 = Arrays.asList(
                new VideoConfig(480) // 480p
                , new VideoConfig(720) // 720p
                , new VideoConfig(1080) // 1080p
                , new VideoConfig(VideoConfig.Track.HD ,2160) // 4K
                , new VideoConfig(VideoConfig.Track.HD ,4320) // 8K
        );

        List<AudioConfig> audioProfile =
                Collections.singletonList(new AudioConfig());


        PallyConConfig pallyConConfig = new PallyConConfig();
        pallyConConfig.setVideoConfigList(videoProfile);
        pallyConConfig.setAudioConfigList(audioProfile);

        CpixModule cpixModule = new CPixCommonModule();
        CpixDTO cpixDTO = cpixModule.getHlsKeyInfo(ENC_TOKEN, CONTENT_ID, pallyConConfig);
//        CpixDTO cpixDTO = cpixModule.getHlsKeyInfo(ENC_TOKEN, CONTENT_ID, pallyConConfig, true, 5);

    }



}