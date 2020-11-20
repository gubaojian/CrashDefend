package com.cainiao.wireless.crashdefendkit.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;
import com.cainiao.wireless.crashdefendkit.lang.AsyncHandler;

/**
 * 异步化处理BroadcastReceiver，减少主现场占用，减少Anr
 * */
public class AsyncBroadcastReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(final Context context, final Intent intent) {
        final PendingResult  result = goAsync();
        try{
            AsyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    doBackgroundReceive(context, intent);
                }
            });
        }catch (Exception e){
            CrashDefendKit.onCrash(AsyncBroadcastReceiver.class, e);
        }finally {
            result.finish();
        }
    }

    public void doBackgroundReceive(Context context, Intent intent){}

}
