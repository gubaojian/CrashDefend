package com.alibaba.motu.crashreporter;

import android.util.Log;

import com.alibaba.motu.crashreporter.page.PageNames;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过反射调用魔图异常上报API
 * 上报CrashDefendSDK和CrashDefend的捕获的异常到魔图平台和ut统计
 * */
public class CrashDefendMoTuAdapter {


    public static void defendReportException(Throwable throwable){
        reportException(PageNames.CRASH_DEFEND_PAGE, throwable);
    }


    /**
     * 上报异常到魔图平台和ut统计
     * */
    public static void reportException(String pageName, Throwable throwable){
        try{
            if(throwable == null){
                return;
            }
            Map<String, String> properties = new HashMap<>();
            properties.put("exceptionType", throwable.getClass().getName()); //统计错误类型
            StatisticHelperAdapter.customHit(pageName, pageName, properties);
            Field reportBuilderField =  CrashReporter.class.getDeclaredField("mReportBuilder");
            if(reportBuilderField == null){
                return;
            }
            reportBuilderField.setAccessible(true);
            ReportBuilder  reportBuilder = (ReportBuilder) reportBuilderField.get(CrashReporter.getInstance());
            if(reportBuilder == null){
                return;
            }
            if(reportBuilder != null){
                Map<String, Object> extraInfo = new HashMap();
                if(throwable instanceof  OutOfMemoryError) {
                    extraInfo.put("threads list", ThreadUtils.getThreadInfos());
                }
                extraInfo.put("SDK_UA",  pageName);
                CrashReport crashReport = reportBuilder.buildUncaughtExceptionReport(throwable, Thread.currentThread(), extraInfo);
                Field  mSendManagerField = CrashReporter.class.getDeclaredField("mSendManager");
                mSendManagerField.setAccessible(true);
                if(mSendManagerField == null){
                    return;
                }
                SendManager mSendManager = (SendManager) mSendManagerField.get(CrashReporter.getInstance());
                if(mSendManager == null){
                    return;
                }
                mSendManager.sendReport(crashReport);
            }
        }catch (Throwable e){
            Log.e("CrashDefendMoTuAdapter", "reportException", e);
        }
    }


    public static void defendKitReportException(String pageName, String category, Throwable throwable){
        if(pageName == null){
            pageName = PageNames.CRASH_KIT_PAGE;
        }
        if(category == null){
            category = "none";
        }
        try{
            if(throwable == null){
                return;
            }
            Map<String, String> properties = new HashMap<>();
            properties.put("exceptionType", throwable.getClass().getName()); //统计错误类型
            properties.put("category", category); //统计错误类型
            StatisticHelperAdapter.customHit(pageName, pageName, properties);
            Field reportBuilderField =  CrashReporter.class.getDeclaredField("mReportBuilder");
            if(reportBuilderField == null){
                return;
            }
            reportBuilderField.setAccessible(true);
            ReportBuilder  reportBuilder = (ReportBuilder) reportBuilderField.get(CrashReporter.getInstance());
            if(reportBuilder == null){
                return;
            }
            if(reportBuilder != null){
                Map<String, Object> extraInfo = new HashMap();
                if(throwable instanceof  OutOfMemoryError) {
                    extraInfo.put("threads list", ThreadUtils.getThreadInfos());
                }
                extraInfo.put("SDK_UA",  pageName);
                CrashReport crashReport = reportBuilder.buildUncaughtExceptionReport(throwable, Thread.currentThread(), extraInfo);
                Field  mSendManagerField = CrashReporter.class.getDeclaredField("mSendManager");
                mSendManagerField.setAccessible(true);
                if(mSendManagerField == null){
                    return;
                }
                SendManager mSendManager = (SendManager) mSendManagerField.get(CrashReporter.getInstance());
                if(mSendManager == null){
                    return;
                }
                mSendManager.sendReport(crashReport);
            }
        }catch (Throwable e){
            Log.e("CrashDefendMoTuAdapter", "reportException", e);
        }
    }

}
