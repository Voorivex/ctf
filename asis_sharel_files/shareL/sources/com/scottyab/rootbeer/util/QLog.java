package com.scottyab.rootbeer.util;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class QLog {
    public static final int ALL = 5;
    public static final int ERRORS_ONLY = 1;
    public static final int ERRORS_WARNINGS = 2;
    public static final int ERRORS_WARNINGS_INFO = 3;
    public static final int ERRORS_WARNINGS_INFO_DEBUG = 4;
    public static int LOGGING_LEVEL = 5;
    public static final int NONE = 0;
    private static final String TAG = "RootBeer";
    private static final String TAG_GENERAL_OUTPUT = "QLog";

    public static void e(Object obj, Throwable th) {
        if (isELoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            String sb2 = sb.toString();
            String str = TAG;
            Log.e(str, sb2);
            Log.e(str, getThrowableTrace(th));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getTrace());
            sb3.append(String.valueOf(obj));
            String sb4 = sb3.toString();
            String str2 = TAG_GENERAL_OUTPUT;
            Log.e(str2, sb4);
            Log.e(str2, getThrowableTrace(th));
        }
    }

    public static void e(Object obj) {
        if (isELoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            Log.e(TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getTrace());
            sb2.append(String.valueOf(obj));
            Log.e(TAG_GENERAL_OUTPUT, sb2.toString());
        }
    }

    public static void w(Object obj, Throwable th) {
        if (isWLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            String sb2 = sb.toString();
            String str = TAG;
            Log.w(str, sb2);
            Log.w(str, getThrowableTrace(th));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getTrace());
            sb3.append(String.valueOf(obj));
            String sb4 = sb3.toString();
            String str2 = TAG_GENERAL_OUTPUT;
            Log.w(str2, sb4);
            Log.w(str2, getThrowableTrace(th));
        }
    }

    public static void w(Object obj) {
        if (isWLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            Log.w(TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getTrace());
            sb2.append(String.valueOf(obj));
            Log.w(TAG_GENERAL_OUTPUT, sb2.toString());
        }
    }

    public static void i(Object obj) {
        if (isILoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            Log.i(TAG, sb.toString());
        }
    }

    public static void d(Object obj) {
        if (isDLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            Log.d(TAG, sb.toString());
        }
    }

    public static void v(Object obj) {
        if (isVLoggable()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTrace());
            sb.append(String.valueOf(obj));
            Log.v(TAG, sb.toString());
        }
    }

    public static boolean isVLoggable() {
        return LOGGING_LEVEL > 4;
    }

    public static boolean isDLoggable() {
        return LOGGING_LEVEL > 3;
    }

    public static boolean isILoggable() {
        return LOGGING_LEVEL > 2;
    }

    public static boolean isWLoggable() {
        return LOGGING_LEVEL > 1;
    }

    public static boolean isELoggable() {
        return LOGGING_LEVEL > 0;
    }

    private static String getThrowableTrace(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static String getTrace() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        String className = stackTrace[2].getClassName();
        int lineNumber = stackTrace[2].getLineNumber();
        String substring = className.substring(className.lastIndexOf(46) + 1);
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(": ");
        sb.append(methodName);
        sb.append("() [");
        sb.append(lineNumber);
        sb.append("] - ");
        return sb.toString();
    }

    public static void handleException(Exception exc) {
        e(exc.toString());
        exc.printStackTrace();
    }

    private QLog() {
    }
}
