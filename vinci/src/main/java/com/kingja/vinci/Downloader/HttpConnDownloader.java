package com.kingja.vinci.Downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description:TODO
 * Create Time:2017/5/6 13:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class HttpConnDownloader implements Downloader {
    @Override
    public Bitmap load(String urlPath) {
        URL url = null;
        Bitmap bitmap = null;
        try {
            url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("GET");
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return bitmap;
    }
}
