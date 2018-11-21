package com.simple.bridge.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.simple.bridge.R;
import com.simple.bridge.js.JsBridge;

public class HybridActivity extends AppCompatActivity {
    private BridgeWebView wvElm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);

        webViewTest();
    }

    /**
     * js原生交互测试
     */
    private void webViewTest() {
//        XLog.i(Thread.currentThread().getName());
        wvElm = (BridgeWebView) findViewById(R.id.wv_elm);
        // 支持调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wvElm.setWebContentsDebuggingEnabled(true);
        }
        // 如果不设置WebViewClient，请求会跳转系统浏览器
        wvElm.setWebViewClient(new WebViewClient());
        // 支持js执行
        wvElm.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wvElm.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        // 注入对象到网页
        wvElm.addJavascriptInterface(new JsBridge(), "jsBridge");
        wvElm.loadUrl("file:///android_asset/jsBridge/JsBridgePage01.html");
    }

    @Override
    protected void onDestroy() {
        wvElm.removeJavascriptInterface("jsBridge");
        super.onDestroy();
    }

    /**
     * 返回事件处理
     */
    @Override
    public void onBackPressed() {
        if(wvElm.canGoBack()) {
            wvElm.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
