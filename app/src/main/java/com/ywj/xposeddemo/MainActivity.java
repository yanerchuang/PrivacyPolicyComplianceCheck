package com.ywj.xposeddemo;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * @author : ywj
 * date   : 2021/1/27/027 14:49
 * desc   :
 */
public class MainActivity extends AppCompatActivity {

    private Button tv;

    public static void getLocalIpAddress() {

        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en != null && en.hasMoreElements(); ) {

                NetworkInterface intf = en.nextElement();
                intf.getHardwareAddress();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {

                        inetAddress.getHostAddress();

                    }

                }

            }

        } catch (SocketException ex) {

            Log.e("WifiPreference IpAddress", ex.toString());

        }


    }
//    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().registerTypeAdapter(String.class, new StringConverter()).create();

//    public static String toJson(Object obj) {
//        return GSON.toJson(obj);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        init();
    }

    private void init() {

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageManager();
                activityManager();
                getLocalIpAddress();
                wifiManager();
                other();
//                telephonyManager();

            }
        });

    }

    private void activityManager() {
        //获取到进程管理器
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        //获取到当前正在运行的任务栈
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);//参数是想获得的个数，可以随意写
        //获取到最上面的进程
        ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
        activityManager.getAppTasks();
        activityManager.getRunningAppProcesses();
        //获取到最顶端应用程序的包名
        String packageName = taskInfo.topActivity.getPackageName();
        Log.d("xxxx ", "taskInfo packageName: " + packageName);
    }

    private void packageManager() {
        PackageManager packageManager = getPackageManager();
        packageManager.getInstalledPackages(1);
        packageManager.getInstalledApplications(1);
    }

    public void telephonyManager() {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                return;
            }
            telephonyManager.getDeviceId();
            telephonyManager.getImei();
            telephonyManager.getNai();
            telephonyManager.getMeid();
            telephonyManager.getSimSerialNumber();
            //在次做个验证，也不是什么时候都能获取到的啊

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public final void wifiManager() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();
        String macAddress = connectionInfo.getMacAddress();

    }

    public final void other() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorManager.getDefaultSensor(Sensor.TYPE_ALL);

        Context cont = this.getApplicationContext();
        String msg = Settings.Secure.getString(cont.getContentResolver(), "android_id");

    }
}
