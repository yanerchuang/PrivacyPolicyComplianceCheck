package com.orhanobut.logger;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.orhanobut.logger.CsvFormatStrategy.Builder.MAX_BYTES;

/**
 * @author: ywj
 * created on: 2018/10/31 10:33
 * description:
 */
public class LoggerUtil {
    private static String TAG =  LoggerUtil.class.getSimpleName();
    private static boolean DEBUG = false;
    private static boolean isInit = false;

    /**
     * @param context
     * @param tag
     * @param isLoggable      是否开启log
     * @param isOpenDiskCache 是否开启缓存
     */
    public static void init(Context context, String tag, final boolean isLoggable, boolean isOpenDiskCache) {
        DEBUG = isLoggable;

        if (isInit) {
            return;
        }
        isInit = true;

        if (!isDebug()) {
            return;
        }

        TAG = tag;
        FormatStrategy logFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //是否显示线程信息 (Optional) Whether to show thread info or not. Default true
                .methodCount(20)         // 显示方法数量 (Optional) How many method line to show. Default 2
//                .methodOffset(5)        // 显示方法数量向上偏移 (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // 打印方法 (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // tag (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(logFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isLoggable;
            }
        });

        if (isOpenDiskCache) {
            String folder = context.getCacheDir().getAbsolutePath() + File.separatorChar + "logger";

            HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
            ht.start();
            Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
            DiskLogStrategy logStrategy = new DiskLogStrategy(handler);
            CsvFormatStrategy csvFormatStrategy = CsvFormatStrategy.newBuilder()
                    .logStrategy(logStrategy)
                    .tag(TAG)
                    .build();

            Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy) {
                @Override
                public boolean isLoggable(int priority, @Nullable String tag) {
                    return isLoggable;
                }
            });
        }
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.v(message, args);
        }
    }

    private static boolean isDebug() {
        if (!DEBUG) {
            Log.e(TAG, "not init LoggerUtil");
        }
        return DEBUG;
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.i(message, args);
        }
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.d(message, args);
        }
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.w(message, args);
        }
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.e(message, args);
        }
    }

    public static void wtf(@NonNull String message, @Nullable Object... args) {
        if (isDebug()) {
            Logger.wtf(message, args);
        }
    }

    public static void json(@NonNull String json) {
        if (isDebug()) {
            Logger.json(json);
        }
    }

    public static void xml(@NonNull String xml) {
        if (isDebug()) {
            Logger.xml(xml);
        }
    }


}
