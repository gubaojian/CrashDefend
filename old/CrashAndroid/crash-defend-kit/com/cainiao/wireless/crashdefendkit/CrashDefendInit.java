package com.cainiao.wireless.crashdefendkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import com.alibaba.motu.crashreporter.CrashDefendMoTuAdapter;
import com.cainiao.wireless.crashdefend.CrashDefendSdk;
import com.cainiao.wireless.crashdefend.OnDefendCatchListener;
import com.cainiao.wireless.crashdefendkit.page.PageNames;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * CrashDefendSDK和CrashDefendKit初始化总入口
 * */
public class CrashDefendInit {



    private static Context mContext;

    /**
     * CrashDefendSDK和CrashDefendKit初始化总入口
     * */
    public static void init(Context context){
        if(context == null){
            return;
        }
        context = context.getApplicationContext();
        mContext = context;
        CrashDefendSdk.getInstance().setOnDefendCatchListener(new OnDefendCatchListener() {
            @Override
            public void onCatch(Throwable throwable) {
                if(throwable != null && isDebug() && mContext != null){
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter  messageWriter = new PrintWriter(stringWriter);
                    throwable.printStackTrace(messageWriter);
                    messageWriter.flush();
                    Toast.makeText(mContext, stringWriter.toString(), Toast.LENGTH_SHORT).show();
                }
                CrashDefendMoTuAdapter.defendReportException(throwable);
            }
        });
        CrashDefendKit.getInstance().setContext(context).setOnCatchCrashListener(new OnCatchCrashListener() {
            @Override
            public void onCatch(String category, Exception e) {
                CrashDefendMoTuAdapter.defendKitReportException(PageNames.CRASH_KIT_PAGE, category, e);
            }
        });
    }


    private static boolean isDebug() {
        try {
            if(mContext == null){
                return false;
            }
            ApplicationInfo info = mContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
