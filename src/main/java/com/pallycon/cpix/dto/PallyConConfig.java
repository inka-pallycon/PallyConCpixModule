package com.pallycon.cpix.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PallyConConfig {

    List<VideoConfig> videoConfigList;
    List<AudioConfig> audioConfigList;

    public List<VideoConfig> getVideoConfigList() {

        // 중복 track 합치기
        List<VideoConfig>  videoProfileList =  new ArrayList();
        for (VideoConfig videoConfig : videoConfigList) {

            // track 기준 group by하여 2차배열 만들기
            List<VideoConfig> videoConfigGroupByList =
                    videoConfigList.stream().collect(Collectors.groupingBy(el -> videoConfig.track.equals(el.track))).get(true);

            // 중복안되게
            long cnt = videoProfileList.stream().filter(el -> videoConfig.track.equals(el.track)).count();
            if ( cnt == 0){

                // minPixels 기준 정렬
                Collections.sort(videoConfigGroupByList);

                // group list 에서 min, max 값 가져오기
                if ( videoConfigGroupByList.size() == 1){
                    videoConfig.minPixels = videoConfigGroupByList.get(0).minPixels;
                    videoConfig.maxPixels = videoConfigGroupByList.get(0).maxPixels;

                    videoProfileList.add(videoConfig);
                }else {

                    videoConfig.minPixels = videoConfigGroupByList.get(0).minPixels;
                    videoConfig.maxPixels = videoConfigGroupByList.get(videoConfigGroupByList.size()-1).maxPixels;

                    videoProfileList.add(videoConfig);
                }
            }
        }
        this.videoConfigList = videoProfileList;


        return videoConfigList;
    }

    public void setVideoConfigList(List<VideoConfig> videoConfigList) {
        this.videoConfigList = videoConfigList;
    }


    public List<AudioConfig> getAudioConfigList() {
        return audioConfigList;
    }

    public void setAudioConfigList(List<AudioConfig> audioConfigList) {
        this.audioConfigList = audioConfigList;
    }

}
