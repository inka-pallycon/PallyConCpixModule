package com.pallycon.cpix.dto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class VideoConfig implements Comparable<VideoConfig>{

    public static class Track{
        public static final String UHD2 = "UHD2";
        public static final String UHD1 = "UHD1";
        public static final String HD = "HD";
        public static final String SD = "SD";

        public static final HashMap<String, Long[]> PIXEL = new LinkedHashMap<String, Long[]>() {
            private static final long serialVersionUID = 1L;
            {
                // ( Track , new Long[]{Min Pixel , Max Pixel} )
                put(UHD2, new Long[]{33177600L, Long.MAX_VALUE});  // 8K
                put(UHD1, new Long[]{8294400L, 8294400L});   // 4K
                put(HD, new Long[]{921600L, 2073600L});    // 720p , 1080p
                put(SD,new Long[]{Long.MIN_VALUE, 414720L});     // 576p
            }
        };

    }

    public String track;
    public String minPixels;
    public String maxPixels;
    public int height;
    public int width;
    public long bitrate;


    /**
     * @param track The track of the video configuration
     * @param height The target output height of the video configuration
     */
    public VideoConfig(
            String track, Integer height){

        track = track.toUpperCase();
        this.track = Track.HD;
        this.height = height;

        Iterator itr = Track.PIXEL.keySet().iterator();
        while(itr.hasNext()) {
            String trackName = (String) itr.next();
            if ( trackName.equals(track) ){
                this.track = track;
                break;
            }
        }

        int inputVideoWidth = 1920;
        int inputVideoHeight = 1080;
        double aspectRatio = inputVideoWidth / (double) inputVideoHeight;
        this.width = (int)(((int) Math.ceil(aspectRatio * this.height) / 2.0) * 2);

        itr = Track.PIXEL.keySet().iterator();
        for(int i=0;i<Track.PIXEL.keySet().size();i++){
            String trackName = (String) itr.next();
            long minPixel = Track.PIXEL.get(trackName)[0];
            long maxPixel = Track.PIXEL.get(trackName)[1];
            long pixel = this.height * this.width;

            if ( minPixel <= pixel && pixel <= maxPixel ){
                this.minPixels = minPixel != Long.MIN_VALUE ? String.valueOf(minPixel) : null;
                this.maxPixels = maxPixel != Long.MAX_VALUE ? String.valueOf(maxPixel) : null;
            }else{
                this.minPixels = String.valueOf(pixel);
                this.maxPixels = String.valueOf(pixel);
            }
        }

    }

    /**
     * @param height The target output height of the video configuration
     */
    public VideoConfig(
            Integer height){

        this.track = Track.HD;  // default
        this.height = height;

        int inputVideoWidth = 1920;
        int inputVideoHeight = 1080;
        double aspectRatio = inputVideoWidth / (double) inputVideoHeight;
        this.width = (int)(((int) Math.ceil(aspectRatio * this.height) / 2.0) * 2);

        Iterator itr = Track.PIXEL.keySet().iterator();
        for(int i=0;i<Track.PIXEL.keySet().size();i++){
            String trackName = (String) itr.next();
            long minPixel = Track.PIXEL.get(trackName)[0];
            long maxPixel = Track.PIXEL.get(trackName)[1];
            long pixel = this.height * this.width;

            if ( minPixel <= pixel && pixel <= maxPixel ){
                this.track = trackName;

                this.minPixels = minPixel != Long.MIN_VALUE ? String.valueOf(minPixel) : null;
                this.maxPixels = maxPixel != Long.MAX_VALUE ? String.valueOf(maxPixel) : null;
            }
        }


    }



    // sort
    public int compareTo(VideoConfig videoConfig) {
        long min = this.minPixels != null ?  Long.parseLong(this.minPixels) : Long.MIN_VALUE;
        long configMin = videoConfig.minPixels != null ?  Long.parseLong(videoConfig.minPixels) : Long.MIN_VALUE;

        if ( min > configMin){
            return 1;
        }else if ( min < configMin){
            return -1;
        }else{
            return 0;
        }
    }

}
