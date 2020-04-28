package com.pallycon.cpix.dto;

public class AudioConfig implements Comparable<AudioConfig>{

    public static class Track{
        public static final String AUDIO = "AUDIO";
    }


    public String track;
    public Long channels;

    public AudioConfig() {
        this.track = Track.AUDIO;
    }

    public int compareTo(AudioConfig audioConfig) {
        if ( this.channels > audioConfig.channels){
            return 1;
        }else if ( this.channels < audioConfig.channels){
            return -1;
        }else{
            return 0;
        }
    }

}
