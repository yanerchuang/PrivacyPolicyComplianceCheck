package com.ywj.xposeddemo;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.orhanobut.logger.LoggerUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author : ywj
 * date   : 2021/1/27/027 14:49
 * desc   :
 */
public class MethodHook implements IXposedHookLoadPackage {

    private static String customTagPrefix = "hookLog ";
    private static String hookMethodTagPrefix = "hookMethod ";


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        final String packageName = lpparam.packageName;
        LoggerUtil.init(null, customTagPrefix, true, false);
        Log.d(customTagPrefix, "start handleLoadPackage: " + packageName);

        ////////////////////////////////////////////////ActivityManager
        XposedHelpers.findAndHookMethod(ActivityManager.class.getName(), lpparam.classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getRunningTasks :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(ActivityManager.class.getName(), lpparam.classLoader, "getAppTasks", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getAppTasks :" + result);
            }
        });

        XposedHelpers.findAndHookMethod(ActivityManager.class.getName(), lpparam.classLoader, "getRunningAppProcesses", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getRunningAppProcesses :" + result);
            }
        });

        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "ApplicationPackageManager getInstalledApplications :" + result);
            }
        });

        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "ApplicationPackageManager getInstalledPackages :" + result);
            }
        });
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpparam.classLoader, "queryIntentActivities", Intent.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "ApplicationPackageManager queryIntentActivities :" + result);
            }
        });


        ////////////////////////////////////////////////InetAddress
        XposedHelpers.findAndHookMethod(InetAddress.class.getName(), lpparam.classLoader, "getHostAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getHostAddress :" + result);
            }
        });
        ////////////////////////////////////////////////NetworkInterface
        XposedHelpers.findAndHookMethod(NetworkInterface.class.getName(), lpparam.classLoader, "getHardwareAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getHardwareAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(NetworkInterface.class.getName(), lpparam.classLoader, "getInetAddresses", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getInetAddresses :" + result);
            }
        });
        ////////////////////////////////////////////////WifiManager
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), lpparam.classLoader, "getIpAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "WifiInfo getIpAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), lpparam.classLoader, "getMacAddress", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "WifiInfo getMacAddress :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(WifiInfo.class.getName(), lpparam.classLoader, "getSSID", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "WifiInfo getSSID :" + result);
            }
        });

        ////////////////////////////////////////////////Settings
        XposedHelpers.findAndHookMethod(Settings.Secure.class.getName(), lpparam.classLoader, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                String args = "";
                for (Object arg : param.args) {
                    args = arg + "-";
                }

                LoggerUtil.d(hookMethodTagPrefix + "Settings Secure getString args:" + args + ",result:" + result);
            }
        });
        XposedHelpers.findAndHookMethod(Settings.System.class.getName(), lpparam.classLoader, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                String args = "";
                for (Object arg : param.args) {
                    args = arg + "-";
                }

                LoggerUtil.d(hookMethodTagPrefix + "Settings System getString args:" + args + ",result:" + result);
            }
        });
        ////////////////////////////////////////////////Settings
        XposedHelpers.findAndHookMethod(SensorManager.class.getName(), lpparam.classLoader, "getSensorList", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "SensorManager getSensorList :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(SensorManager.class.getName(), lpparam.classLoader, "getDefaultSensor", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getDefaultSensor :" + result);
            }
        });

        ////////////////////////////////////////////////TelephonyManager
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getDeviceId", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getDeviceId :" + result);
            }
        });


        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getDeviceId", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getDeviceId(int) :" + result);
                    }
                }
        );

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getImei", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getImei :" + result);
            }
        });

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getMeid", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getMeid :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getNai", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getNai :" + result);
            }
        });
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getSimSerialNumber", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.getResult();
                LoggerUtil.d(hookMethodTagPrefix + "getSimSerialNumber :" + result);
            }
        });

        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getSubscriberId", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getSubscriberId获取了imsi :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getLine1Number", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getLine1Number :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getNetworkOperator", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getNetworkOperator :" + result);
                    }
                }
        );
        XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), lpparam.classLoader, "getNetworkOperatorName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getNetworkOperatorName :" + result);
                    }
                }
        );

        ////////////////////////////////////////////////LocationManager
        XposedHelpers.findAndHookMethod(LocationManager.class.getName(), lpparam.classLoader, "getLastKnownLocation", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Object result = param.getResult();
                        LoggerUtil.d(hookMethodTagPrefix + "getLastKnownLocation :" + result);
                    }
                }
        );
    }
}
