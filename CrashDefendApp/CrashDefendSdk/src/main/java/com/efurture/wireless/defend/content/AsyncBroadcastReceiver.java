package com.efurture.wireless.defend.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.efurture.wireless.defend.DefendReporter;
import com.efurture.wireless.defend.lang.AsyncHandler;

/**
 * 异步化处理BroadcastReceiver，减少主现场占用，减少Anr
 * */
public abstract class AsyncBroadcastReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(final Context context, final Intent intent) {
        final PendingResult  result = goAsync();
        try{
            AsyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    doReceiveBackground(context, intent);
                }
            });
        }catch (Exception e){
            DefendReporter.onCrash(e);
        }finally {
            result.finish();
        }
    }

    public abstract void doReceiveBackground(Context context, Intent intent);

}
