package com.simple.bridge.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class BridgeWebView extends WebView{
    public BridgeWebView(Context context) {
        super(context);
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
