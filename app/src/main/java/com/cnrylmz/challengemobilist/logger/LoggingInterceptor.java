package com.cnrylmz.challengemobilist.logger;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Platform;

/**
 * Created by caner on 7.08.2017.
 */

public class LoggingInterceptor implements Interceptor {

    private boolean isDebug;
    private Builder builder;

    private LoggingInterceptor(Builder builder) {
        this.builder = builder;
        this.isDebug = builder.isDebug;
    }



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (builder.getHeaders().size() > 0) {
            Headers headers = request.headers();
            Set<String> names = headers.names();
            Iterator<String> iterator = names.iterator();
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.headers(builder.getHeaders());
            while (iterator.hasNext()) {
                String name = iterator.next();
                requestBuilder.addHeader(name, headers.get(name));
            }
            request = requestBuilder.build();
        }

        if (!isDebug || builder.getLevel() == Level.NONE) {
            return chain.proceed(request);
        }
        RequestBody requestBody = request.body();

        MediaType rContentType = null;
        if (requestBody != null) {
            rContentType = request.body().contentType();
        }

        String rSubtype = null;
        if (rContentType != null) {
            rSubtype = rContentType.subtype();
        }

        if (rSubtype != null && (rSubtype.contains("json")
                || rSubtype.contains("xml")
                || rSubtype.contains("plain")
                || rSubtype.contains("html"))) {
            Printer.printJsonRequest(builder, request);
        } else {
            Printer.printFileRequest(builder, request);
        }

        long st = System.nanoTime();
        Response response = chain.proceed(request);

        List<String> segmentList = ((Request) request.tag()).url().encodedPathSegments();
        long chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st);
        String header = response.headers().toString();
        int code = response.code();
        boolean isSuccessful = response.isSuccessful();
        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();

        String subtype = null;
        ResponseBody body;

        if (contentType != null) {
            subtype = contentType.subtype();
        }

        if (subtype != null && (subtype.contains("json")
                || subtype.contains("xml")
                || subtype.contains("plain")
                || subtype.contains("html"))) {
            String bodyString = Printer.getJsonString(responseBody.string());
            Printer.printJsonResponse(builder, chainMs, isSuccessful, code, header, bodyString, segmentList);
            body = ResponseBody.create(contentType, bodyString);
        } else {
            Printer.printFileResponse(builder, chainMs, isSuccessful, code, header, segmentList);
            return response;
        }

        return response.newBuilder().body(body).build();
    }

    @SuppressWarnings("unused")
    public static class Builder {

        private static String TAG = "HttpLogging";
        private boolean isDebug;
        private int type = Platform.INFO;
        private String requestTag;
        private String responseTag;
        private Level level = Level.BASIC;
        private Headers.Builder builder;
        private Logger logger;

        public Builder() {
            builder = new Headers.Builder();
        }

        int getType() {
            return type;
        }

        Level getLevel() {
            return level;
        }

        Headers getHeaders() {
            return builder.build();
        }

        String getTag(boolean isRequest) {
            if (isRequest) {
                return TextUtils.isEmpty(requestTag) ? TAG : requestTag;
            } else {
                return TextUtils.isEmpty(responseTag) ? TAG : responseTag;
            }
        }

        Logger getLogger() {
            return logger;
        }

        public Builder addHeader(String name, String value) {
            builder.set(name, value);
            return this;
        }

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }


        public Builder tag(String tag) {
            TAG = tag;
            return this;
        }


        public Builder request(String tag) {
            this.requestTag = tag;
            return this;
        }


        public Builder response(String tag) {
            this.responseTag = tag;
            return this;
        }

        public Builder loggable(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }


        public Builder log(int type) {
            this.type = type;
            return this;
        }


        public Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public LoggingInterceptor build() {
            return new LoggingInterceptor(this);
        }


    }


}
