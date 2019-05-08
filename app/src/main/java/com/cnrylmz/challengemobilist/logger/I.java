package com.cnrylmz.challengemobilist.logger;

import okhttp3.internal.Platform;

/**
 * Created by caner on 7.08.2017.
 */

public class I {
    protected I() {
        throw new UnsupportedOperationException();
    }

    static void log(int type, String tag, String msg) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tag);
        switch (type) {
            case Platform.INFO:
                logger.log(java.util.logging.Level.INFO, msg);
                break;
            default:
                logger.log(java.util.logging.Level.WARNING, msg);
                break;
        }
    }
}
