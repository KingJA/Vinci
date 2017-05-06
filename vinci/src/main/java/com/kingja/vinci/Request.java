package com.kingja.vinci;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Description:TODO
 * Create Time:2017/5/4 10:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Request implements Comparable<Request> {
    public String url;
    public int placeholderRes;
    public int errorRes;
    public ImageView imageView;
    public Context context;


    public Request(Builder builder) {
        this.url = builder.url;
        this.placeholderRes = builder.placeholderResId;
        this.errorRes = builder.errorRes;
        this.imageView = builder.imageView;
        this.context = builder.context;
    }

    @Override
    public int compareTo(@NonNull Request request) {
        return 1;
    }

    public static class Builder {
        private String url;
        private int placeholderResId;
        private int errorRes;
        private Context context;
        public ImageView imageView;

        public Builder(String url) {
            this.url = url;
        }

        public Builder placeholderRes(@DrawableRes int placeholderResId) {
            this.placeholderResId = placeholderResId;
            return this;
        }

        public Builder errorRes(@DrawableRes int errorRes) {
            this.errorRes = errorRes;
            return this;
        }

        public Builder setTarget(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Request build() {
            return new Request(this);
        }


    }
}
