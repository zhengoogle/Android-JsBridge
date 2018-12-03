package com.simple.jsbridge;

import android.content.Intent;
import android.os.Bundle;

import com.simple.bridge.view.HybridActivity;
import com.simple.jsbridge.base.utils.PathUtils;
import com.simple.fwlibrary.base.comp.FwPermissionActivity;
import com.simple.fwlibrary.log.xlog.XLog;


public class MainActivity extends FwPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getMainView() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {
        logAppEnvironmentInfo();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadDatas() {
        Intent intent = new Intent(this,HybridActivity.class);
        startActivity(intent);
        finish();

//        try {
//            FwMiniHttpServer.getInstance(8080).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 打印APP运行环境信息
     */
    private void logAppEnvironmentInfo(){
        XLog.i(getAppInfo());
        XLog.i(getSystemInfo());
    }

    /**
     * 获取App信息
     * @return
     */
    private String getAppInfo(){
        StringBuilder stringBuilder = new StringBuilder("AppInfo:\n");
        stringBuilder.append("VersionCode:" + BuildConfig.VERSION_CODE + "\n");
        stringBuilder.append("VersionName:" + BuildConfig.VERSION_NAME + "\n");
        if(BuildConfig.DEBUG){
            stringBuilder.append("LogPath:").append(PathUtils.getLogPath()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取系统信息
     * @return
     */
    private String getSystemInfo(){
        StringBuilder stringBuilder = new StringBuilder("SystemInfo:\n");
        stringBuilder.append("VersionCode:" + BuildConfig.VERSION_CODE + "\n");
        stringBuilder.append("VersionName:" + BuildConfig.VERSION_NAME + "\n");
        return stringBuilder.toString();
    }
}
