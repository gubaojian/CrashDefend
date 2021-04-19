package com.efurture.wireless.defend.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cainiao.wireless.crashdefend.app.R;
import com.efurture.wireless.defend.app.test.AsyncTaskBgTest;
import com.efurture.wireless.defend.app.test.AsyncTaskUITest;
import com.efurture.wireless.defend.app.test.ThreadTest;
import com.efurture.wireless.defend.redirect.MethodCallDispatcher;
import com.efurture.wireless.defend.redirect.MethodDispatcherAdapter;

import java.lang.reflect.InvocationTargetException;

public class FirstFragment extends Fragment {


    private Handler mHandler;

    private int MSG_WHAT = 100;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == MSG_WHAT) {
                    throw new RuntimeException("handleMessage");
                }
                return false;
            }
        });
        view.findViewById(R.id.npe_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException("Hello World NPE");
            }
        });

        view.findViewById(R.id.run_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        throw new RuntimeException("Runnable Test Exception");
                    }
                });
            }
        });
        view.findViewById(R.id.view_run_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.view_run_test).post(new Runnable() {
                    @Override
                    public void run() {
                        throw new RuntimeException("View Post Runnable Test Exception");
                    }
                });
            }
        });


        view.findViewById(R.id.async_bg_run_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskBgTest asyncTaskBgTest = new AsyncTaskBgTest();
                asyncTaskBgTest.execute();
            }
        });

        view.findViewById(R.id.async_ui_run_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskUITest asyncTaskUITest = new AsyncTaskUITest();
                asyncTaskUITest.execute();
            }
        });

        view.findViewById(R.id.hander_post_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        throw new RuntimeException("Handler Post Runnable Test Exception");
                    }
                });
            }
        });

        view.findViewById(R.id.hander_send_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_WHAT);
            }
        });

        view.findViewById(R.id.thread_run_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ThreadTest().start();
            }
        });

        view.findViewById(R.id.third_party_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodCallDispatcher.getInstance().setMethodDispatcherAdapter(new MethodDispatcherAdapter(){
                    @Override
                    public Object dispatchMethodCall(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                        Toast.makeText(getActivity(), "android.provider.Settings$Secure.getString 被拦截啦", Toast.LENGTH_SHORT).show();
                        if(true){
                            return  "获取了虚拟假冒的AndroidId";
                        }
                        return super.dispatchMethodCall(target, methodName, isStatic, parameterTypes, args);
                    }
                });
                requestPermissions(new String[]{
                        Manifest.permission.READ_PHONE_STATE
                }, 0);
                TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String meid = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
                    Toast.makeText(getActivity(), meid, Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.activit_party_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodCallDispatcher.getInstance().setMethodDispatcherAdapter(new MethodDispatcherAdapter(){
                    @Override
                    public Object dispatchMethodCall(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                        Toast.makeText(getActivity(), "Activity 调用被拦截啦", Toast.LENGTH_SHORT).show();
                        return super.dispatchMethodCall(target, methodName, isStatic, parameterTypes, args);
                    }
                });
                Intent intent = new Intent(getActivity(), TestThreeActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}