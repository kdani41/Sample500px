package com.d500px.fivehundredpx.utils;

import com.d500px.fivehundredpx.BuildConfig;

import android.util.Log;

/**
 * Created by KD on 2/5/16.
 */
public class LogUtils {

    /**
     * Log to the debug console with the passed in tag and text
     *
     * @param tag  the tag to use
     * @param text the text to print to the console
     */
    public static void debug(String tag, String text) {
        if (BuildConfig.DEBUG) {
            Log.d(tag + ": ", text);
        }
    }

    /**
     * Log to the error console with the passed in tag and text
     *
     * @param tag  the tag to use
     * @param text the text to print to the console
     */
    public static void error(String tag, String text) {
        if (BuildConfig.DEBUG) {
            Log.e(tag + ": ", text);
        }
    }
}
