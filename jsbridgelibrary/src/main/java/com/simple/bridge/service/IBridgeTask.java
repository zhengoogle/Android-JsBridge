package com.simple.bridge.service;

import com.simple.bridge.js.JsParams;

public interface IBridgeTask {
    String runSyncTasks(JsParams jsParams);

    String runAsyncTasks(JsParams jsParams);
}
