package com.simple.jsbridge.bridge.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.simple.fwlibrary.base.comp.FwCompActivity;
import com.simple.fwlibrary.log.xlog.XLog;
import com.simple.jsbridge.R;
import com.simple.jsbridge.bridge.js.NativeObj;

public class WebViewBaseActivity extends FwCompActivity {
    private WebView wvElm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getMainView() {
        return R.layout.activity_web_view_base;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadDatas() {
        webViewTest();
    }

    /**
     * js原生交互测试
     */
    private void webViewTest() {
        XLog.i(Thread.currentThread().getName());
        wvElm = (WebView) findViewById(R.id.wv_elm);
        // 定义注入的对象
        class JsObject {
            public int callCounter = 0;

            // 支持返回值
            // 执行在JavaBridge线程，非main线程
            @JavascriptInterface
            public String getLightObject() {
                callCounter++;
                return Thread.currentThread().getName() + " " + NativeObj.lightObj + callCounter;
            }

            // 支持传参-返回值
            // js执行时间1-2ms on KiRin925
            @JavascriptInterface
            public String getHeavyObject(String params) {
                callCounter++;
                return NativeObj.heavyObj + callCounter;
            }

            // !!!传参要一致
            @JavascriptInterface
            public boolean callJavaNative(String params) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 需要在主线程执行
                                wvElm.loadUrl("javascript:runOnJavaNative(123456)");
                            }
                        });
                    }
                }).start();
                return true;
            }
        }
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
        wvElm.addJavascriptInterface(new JsObject(), "injectedObject");
        wvElm.loadUrl("file:///android_asset/jsBridge/JsBridgePage01.html");
    }

    @Override
    protected void onDestroy() {
        wvElm.removeJavascriptInterface("injectedObject");
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
