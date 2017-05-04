package com.kingja.vinci.Cache;

import android.graphics.Bitmap;

/**
 * Description:TODO
 * Create Time:2017/5/4 13:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Cache {
    Bitmap get(String key);

    void set(String key, Bitmap bitmap);

    int size();

    int maxSize();

    void clear();

    void clearKeyUri(String keyPrefix);
}
