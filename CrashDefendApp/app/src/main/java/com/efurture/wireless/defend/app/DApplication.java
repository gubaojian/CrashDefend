package com.efurture.wireless.defend.app;

import android.app.Application;

import com.alibaba.motu.crashreporter.CrashDefendMoTuAdapter;
import com.efurture.wireless.defend.OnCatchCrashListener;
import com.efurture.wireless.defend.annotation.Defend;
import com.efurture.wireless.defend.CrashDefendSdk;


@Defend
public class DApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashDefendSdk.getInstance().setContext(getApplicationContext()).setOnCatchCrashListener(new OnCatchCrashListener() {
            @Override
            public void onCatch(Throwable throwable) {
                // TODO 上报异常
                //CrashDefendMoTuAdapter.defendReportException(throwable);
            }
        }).init();


    }
}
