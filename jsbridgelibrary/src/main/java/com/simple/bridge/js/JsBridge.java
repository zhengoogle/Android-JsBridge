package com.simple.bridge.js;

import android.webkit.JavascriptInterface;

public class JsBridge implements IBridge{
    @Override
    @JavascriptInterface
    public String bridge(String params) {
        return null;
    }

    @Override
    @JavascriptInterface
    public String bridgeDynamic(String params) {
        return null;
    }
}
