package com.kingja.vinci.Downloader;

import java.io.IOException;

import okhttp3.Response;

/**
 * Description:TODO
 * Create Time:2017/5/4 10:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Downloader {
     Response load(String url) throws IOException;
}
