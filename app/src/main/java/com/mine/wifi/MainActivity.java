package com.mine.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout bg;
    private TextView tv;
    private View loa;
    private View img;
    private List<String> list;
    private int loaWidth;
    private int loaHeight;
    private String url = "http://42.193.9.98:8002/zhaozhiwen?key=1@1231dwe323e5dK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyPermissionUtils.init();
        list = new ArrayList<>();
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        list.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        list.add(Manifest.permission.ACCESS_WIFI_STATE);
        MyPermissionUtils.cheAndReqPermission(this, list, 1001);
        Button btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);
        bg = findViewById(R.id.img_bg);
        img = findViewById(R.id.img);
        loa = LayoutInflater.from(this).inflate(R.layout.loa, null);
        loaWidth = loa.getWidth();
        loaHeight = loa.getHeight();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPermissionUtils.cheAndReqPermission(MainActivity.this, list, 1001);
//                点击按钮开始扫描wifi
                scanWifiInfo();
            }
        });

    }
//设置定位图位置
    private void setLoa(int x1, int y1) {
        try {
            if (loa.getParent() == bg) {
                bg.removeView(loa);
            }
        } catch (Exception e) {
        }
//        1000:600  5:3
        int height = img.getHeight();
        int width = img.getWidth();
        int x = (int) ((double) width / 1000 * x1);
        int y = (int) ((double) height / 600 * y1);
        loa.setX(x - (loaWidth / 2));
        loa.setY(height - y + (loaHeight / 2));
        bg.addView(loa);
//        tv.setText("x:" + loa.getX() + "\ty:" + loa.getY());
    }
//扫描wifi
    private void scanWifiInfo() {
        tv.setText("");
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            String str = "";
            for (int i = 0; i < scanWifiList.size(); i++) {
                if (i > 5) {
                    break;
                }
                ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    str += "&r" + (i + 1) + "=" + scanResult.level;
                    tv.append(i + ".SSID:" + scanResult.SSID + "\tRSSI：" + scanResult.level + "\n");
                }
            }
//            根据wifi RSSI去请求服务器指纹库
            getData(str);
        } else {
            tv.setText("没有搜索到wifi");
        }

    }

//    匹配服务器指纹库
    private void getData(String str) {
        OkGo.<String>get(url + str).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
//                获取坐标
                LoaBean loaBean = new Gson().fromJson(response.body(), LoaBean.class);
                int x = Integer.parseInt(loaBean.getData().getX());
                int y = Integer.parseInt(loaBean.getData().getY());
//                设置图标
                setLoa(x, y);
                tv.append("坐标:(" + x + "," + y + ")");
            }
        });
    }

}
