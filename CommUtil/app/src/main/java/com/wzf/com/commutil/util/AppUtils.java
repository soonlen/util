package com.wzf.com.commutil.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppUtils {

    /**
     * 查看activity在后台还是前台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100 
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200 
				 */
                L.i("此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    L.i("处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    L.i("处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 查看activity是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isRunning(Context context, String clazz) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        L.e("cName:" + clazz);
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static long getApkVersionCode(Context context) {
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }

    /**
     * 获取应用的版本号名称
     *
     * @param context
     * @return
     */
    public static String getApkVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取APK的版本号
     *
     * @param context
     * @param archiveFilePath
     * @return
     */
    public static long getApkVersonCode(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
//            ApplicationInfo appInfo = info.applicationInfo;
//            String appName = pm.getApplicationLabel(appInfo).toString();
//            String packageName = appInfo.packageName;  //得到安装包名称
//            String version = info.versionName;       //得到版本信息
            return info.versionCode;
        }
        return 0;
    }

}
