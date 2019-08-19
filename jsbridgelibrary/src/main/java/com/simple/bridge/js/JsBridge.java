package com.simple.bridge.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.simple.bridge.base.JsonUtils;
import com.simple.bridge.base.MLog;
import com.simple.bridge.service.NativeTest;
import com.simple.bridge.view.BridgeWebView;

import java.lang.reflect.Method;

/**
 * jsBridge for WebAPPS
 */
public class JsBridge implements IBridge{
    private Context context;
    private BridgeWebView bridgeWebView; // WebView

    public JsBridge(Context context,BridgeWebView bridgeWebView) {
        this.context = context;
        this.bridgeWebView = bridgeWebView;
    }

    /**
     * jsBridge本地同步方法
     * ---普通调用
     * @param params 网页入参
     * @return
     */
    @Override
    @JavascriptInterface
    public String sync(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setObject(context);
        return new NativeTest().runSyncTasks(jsParams);
    }

    /**
     * jsBridge本地异步方法
     * ---普通调用
     * @param params 网页入参
     * @return
     */
    @Override
    @JavascriptInterface
    public String async(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setObject(bridgeWebView);
        return new NativeTest().runAsyncTasks(jsParams);
    }

    /**
     * jsBridge本地同步方法
     * ---反射调用
     * @param params 网页入参
     * @return
     */
    @Override
    @JavascriptInterface
    public String syncBridge(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setObject(context);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jsParams.getPkg() + "." + jsParams.getService());
            Method method = clazz.getMethod(jsParams.getMethod(), JsParams.class);
            return method.invoke(clazz.newInstance(),jsParams).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * jsBridge本地异步方法
     * ---反射调用
     * @param params 网页入参
     * @return
     */
    @Override
    @JavascriptInterface
    public String asyncBridge(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setObject(bridgeWebView);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jsParams.getPkg() + "." + jsParams.getService());
            Method method = clazz.getMethod(jsParams.getMethod(), JsParams.class);
            return method.invoke(clazz.newInstance(),jsParams) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
