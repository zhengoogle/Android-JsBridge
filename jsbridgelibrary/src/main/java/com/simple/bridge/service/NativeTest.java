package com.simple.bridge.service;

import com.simple.bridge.js.JsParams;
import com.simple.bridge.view.BridgeWebView;

public class NativeTest implements IBridgeTask{
    @Override
    public String runSyncTasks(JsParams jsParams) {
        return "runSyncTasks...";
    }

    @Override
    public String runAsyncTasks(JsParams jsParams) {
        ((BridgeWebView)jsParams.getObject()).execWebCallback(jsParams.getTaskId(),"res from native...");
        return "";
    }
}
