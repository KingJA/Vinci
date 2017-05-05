package com.kingja.vinci.Downloader;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:TODO
 * Create Time:2017/5/4 11:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class OkHttpDownloader implements Downloader {

    private final OkHttpClient okHttpClient;

    public OkHttpDownloader() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        okHttpClient = builder.readTimeout(60000, TimeUnit.MILLISECONDS).connectTimeout(60000, TimeUnit.MILLISECONDS)
                .build();

    }

    @Override
    public Response load(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return okHttpClient.newCall(request).execute();
    }
}
