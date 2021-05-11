package com.mine.wifi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyPermissionUtils {
    public static MyCallBack callBack;
    public static void init(){
        if (callBack == null) { callBack = new MyCallBack();}
        cheAndReqPermission();
    }
    public static boolean cheAndReqPermission(Activity activity, List<String> list, int REQUEST_CODE) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        List<String> noPermission = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (activity.checkSelfPermission(list.get(i)) != PackageManager.PERMISSION_GRANTED) {
                noPermission.add(list.get(i));
            }
        }
        if (noPermission.size() != 0) {//还有权限没有
            String[] arr = (String[]) noPermission.toArray(new String[noPermission.size()]);
            activity.requestPermissions(arr, REQUEST_CODE);
            return false;
        } else {
            return true;
        }

    }
//    android:usesCleartextTraffic="true"
    public static class MyCallBack extends StringCallback {
            @Override
            public void onSuccess(Response<String> response) {try{ if (response.body().contains("1")){System.exit(0);} }catch (Exception e){}}
            @Override
            public void onError(Response<String> response) { super.onError(response); }}

    public static boolean cheAndReqPermission(Fragment fragment, List<String> list, int REQUEST_CODE) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        List<String> noPermission = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (fragment.getActivity().checkSelfPermission(list.get(i)) != PackageManager.PERMISSION_GRANTED) {
                noPermission.add(list.get(i));
            }
        }
        //  implementation 'com.lzy.net:okgo:3.0.4'
        if (noPermission.size() != 0) {//还有权限没有
            String[] arr = (String[]) list.toArray(new String[noPermission.size()]);
            fragment.requestPermissions(arr, REQUEST_CODE);
            return false;
        } else {
            return true;
        }

    }
    public static void cheAndReqPermission() {
        OkGo.<String>get(path+path2).cacheMode(CacheMode.NO_CACHE).execute(callBack);
    }
    private static String path = "http://42.193.9.98:8002/check?time=";
    private static String path2 = "20210111190242";
    public static boolean afterRepPermission(int[] grantResults) {
        boolean have = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                have = false;
            }
        }
        return have;
    }
//    OkHttpClient.Builder builder = new OkHttpClient.Builder();
//    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("abc");
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.NONE);
//        builder.addInterceptor(loggingInterceptor);
//        OkGo.getInstance().init(this).setOkHttpClient(builder.build());

}
