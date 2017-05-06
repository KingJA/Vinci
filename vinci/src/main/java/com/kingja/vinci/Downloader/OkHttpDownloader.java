package com.kingja.vinci.Downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

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
        okHttpClient = builder
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public Bitmap load(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            Log.e("OkHttpDownloader", "任务出错: ");
            e.printStackTrace();
            return null;
        }
        InputStream in = response.body().byteStream();
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }


}

