package com.d500px.fivehundredpx.utils;

import android.os.Bundle;

import java.net.InetAddress;

/**
 * Created by KD on 2/10/16.
 */
public class CommonUtils {
    /**
     * Method to check if internet is available.
     *
     * @return Returns true if the internet is available
     */
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            return !address.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean bundleContainsKey(Bundle bundle, String key) {
        return bundle != null && bundle.containsKey(key);
    }
}
