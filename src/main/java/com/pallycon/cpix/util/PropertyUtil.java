package com.pallycon.cpix.util;

import java.util.ResourceBundle;

public class PropertyUtil {

    private static ResourceBundle messageBundle;

    static {
        if(messageBundle==null) {

            if ( System.getProperty("spring.profiles.active") == null  ) {
                messageBundle = ResourceBundle.getBundle("config");
            }else{
                messageBundle = ResourceBundle.getBundle("config-"+System.getProperty("spring.profiles.active"));
            }
        }
    }

    public static String get(String key) {
        try {
            String returnString = "";
            returnString = messageBundle.getString(key);

            return returnString;
        } catch (Exception ex) {
            return null;
        }
    }

}
