package com.asisctf.ShareL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import androidx.core.os.EnvironmentCompat;
import java.util.Random;

public class Utils {
    public static String BASE_URL = "http://66.172.33.148:5001";

    public static void setBaseUrl(String str) {
        BASE_URL = str;
    }

    public static String getAuth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("register", 0);
        String string = sharedPreferences.getString("auth", null);
        int i = sharedPreferences.getInt("uid", 1000);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(".");
        sb.append(string);
        return sb.toString();
    }

    public static String getUid(Context context) {
        int i = context.getSharedPreferences("register", 0).getInt("uid", 1000);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("");
        return sb.toString();
    }

    public static String getDeviceId(Context context) {
        return CryptoHandler.md5(Secure.getString(context.getContentResolver(), "android_id"));
    }

    public static boolean isEmulator() {
        String str = "generic";
        if (!Build.FINGERPRINT.startsWith(str) && !Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN)) {
            String str2 = "google_sdk";
            if (!Build.MODEL.contains(str2) && !Build.MODEL.contains("Emulator") && !Build.MODEL.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion") && ((!Build.BRAND.startsWith(str) || !Build.DEVICE.startsWith(str)) && !str2.equals(Build.PRODUCT))) {
                return false;
            }
        }
        return true;
    }

    public static int getRandom() {
        if (isEmulator()) {
            return new Random().nextInt(14) + 1;
        }
        return new Random().nextInt(900000000) + 100000000;
    }
}
