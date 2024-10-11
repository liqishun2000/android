package com.example.core.utils;

import android.util.Log;

public class TraceLogUtils {

    private static final String TAG = "...";

    public static void log(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //本身
        StackTraceElement stackTraceElement = stackTrace[3];
        String fun = stackTraceElement.getMethodName();
        String className = stackTraceElement.getFileName().split("\\.")[0];

        //上一层
        StackTraceElement stackTraceElementParent;
        String funParent = "", classNameParent = "";
        for (int i = 4; i < stackTrace.length; i++) {
            stackTraceElementParent = stackTrace[i];
            if (stackTraceElementParent.getMethodName().contains("$")) {
                continue;
            }
            funParent = stackTraceElementParent.getMethodName();
            classNameParent = stackTraceElementParent.getFileName().split("\\.")[0];
            break;
        }


        Log.d(TAG, classNameParent + "." + funParent + ": " + className + " -> " + fun + ": " + msg);
    }

    public static void progressLog(String tag, String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //本身
        StackTraceElement stackTraceElement = stackTrace[3];
        String fun = stackTraceElement.getMethodName();
        String className = stackTraceElement.getFileName().split("\\.")[0];

        //上一层
        StackTraceElement stackTraceElementParent;
        String funParent = "", classNameParent = "";
        for (int i = 4; i < stackTrace.length; i++) {
            stackTraceElementParent = stackTrace[i];
            if (stackTraceElementParent.getMethodName().contains("$")) {
                continue;
            }
            funParent = stackTraceElementParent.getMethodName();
            classNameParent = stackTraceElementParent.getFileName().split("\\.")[0];
            break;
        }

        Log.d(tag, classNameParent + "." + funParent + ": " + className + " -> " + fun + ": " + msg);
    }

}
