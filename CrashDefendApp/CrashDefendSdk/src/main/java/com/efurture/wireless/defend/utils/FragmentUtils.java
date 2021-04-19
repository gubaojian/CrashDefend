package com.efurture.wireless.defend.utils;

import android.app.DialogFragment;
import android.app.Fragment;

import com.efurture.wireless.defend.DefendReporter;

public class FragmentUtils {


    /**
     * Fragment 是否有效, 或者已销毁
     * */
    public static final boolean isFragmentValid(Fragment fragment){
        if(fragment == null){
            return false;
        }
        if(fragment.getActivity() == null){
            return false;
        }

        if(!fragment.isAdded()){
            return false;
        }

        if(fragment.isDetached()){
            return false;
        }

        return true;
    }


    /**
     * 隐藏Dialog
     * */
    public static final void dismiss(DialogFragment fragment){
        if(fragment == null){
            return;
        }
        try {
            fragment.dismissAllowingStateLoss();
        }catch (Exception e){
            DefendReporter.onCrash(e);
        }
    }
}
