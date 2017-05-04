package com.kingja.vinci.Cache;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kingja.vinci.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Description:TODO
 * Create Time:2017/5/4 13:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LruCache implements Cache {
    LinkedHashMap<String, Bitmap> map;
    private int maxSize;
    private int size;
    private int putCount;
    private int evictionCount;
    private int hitCount;
    private int missCount;

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }


    @Override
    public Bitmap get(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }

        Bitmap mapValue;
        synchronized (this) {
            Log.e(TAG, "查询缓存: " + map.size());
            mapValue = map.get(key);
            if (mapValue != null) {
                Log.e(TAG, "取出缓存: " + key);
                hitCount++;
                return mapValue;
            }
            missCount++;
        }

        return null;
    }

    @Override
    public void set(@NonNull String key, @NonNull Bitmap bitmap) {

        if (key == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }

        int addedSize = Utils.getBitmapBytes(bitmap);
        if (addedSize > maxSize) {
            return;
        }

        synchronized (this) {
            Log.e(TAG, "maxSize: " + maxSize+" addedSize: " + addedSize);
            Log.e(TAG, "存入缓存: " + key);
            putCount++;
            size += addedSize;
            Bitmap previous = map.put(key, bitmap);
            if (previous != null) {
                size -= Utils.getBitmapBytes(previous);
            }
        }
        Log.e(TAG, "保存后缓存: " + map.size());
        trimToSize(maxSize);
    }

    private void trimToSize(int maxSize) {
        while (true) {
            String key;
            Bitmap value;
            synchronized (this) {
                if (size < 0 || (map.isEmpty() && size != 0)) {
                    throw new IllegalStateException(
                            getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

                if (size <= maxSize || map.isEmpty()) {
                    break;
                }

                Map.Entry<String, Bitmap> toEvict = map.entrySet().iterator().next();
                key = toEvict.getKey();
                value = toEvict.getValue();
                map.remove(key);
                size -= Utils.getBitmapBytes(value);
                evictionCount++;
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int maxSize() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearKeyUri(String keyPrefix) {

    }


}
