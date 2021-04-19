package com.cainiao.wireless.crashdefendkit.os;

import android.os.AsyncTask;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AsyncTaskHook {

    /**
     * 安全并行执行容器
     * */
    public static final void safeSerialThreadPool(){
        SafeSerialExecutor safeSerialExecutor = new SafeSerialExecutor();
        try{
            Field field = AsyncTask.class.getDeclaredField("SERIAL_EXECUTOR");
            if(field != null){
                try{
                    field.setAccessible(true);
                    Field artField = Field.class.getDeclaredField("artField");
                    artField.setAccessible(true);
                    Object artFieldValue = artField.get(field);
                    Field  accessFlagsFiled = artFieldValue.getClass().getDeclaredField("accessFlags");
                    accessFlagsFiled.setAccessible(true);
                    accessFlagsFiled.setInt(artFieldValue, field.getModifiers()&~ Modifier.FINAL);
                    field.set(null, safeSerialExecutor);
                }catch (Exception e){
                    CrashDefendKit.onCrash(AsyncTaskHook.class, e);
                }
            }
            Field defaultField  = AsyncTask.class.getDeclaredField("sDefaultExecutor");
            defaultField.setAccessible(true);
            defaultField.set(null, safeSerialExecutor);
        }catch (Exception e){
            CrashDefendKit.onCrash(AsyncTaskHook.class, e);
        }
    }
}
