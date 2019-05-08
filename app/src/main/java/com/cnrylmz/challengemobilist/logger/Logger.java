package com.cnrylmz.challengemobilist.logger;

import okhttp3.internal.Platform;

/**
 * Created by caner on 7.08.2017.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface Logger {
    void log(int level, String tag, String msg);

    Logger DEFAULT = new Logger() {
        @Override
        public void log(int level, String tag, String message) {
            Platform.get().log(level, message, null);
        }
    };
}