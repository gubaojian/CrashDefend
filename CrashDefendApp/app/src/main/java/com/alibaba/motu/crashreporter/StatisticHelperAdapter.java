package com.alibaba.motu.crashreporter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.motu.crashreporter.page.PageNames;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders;
import com.ut.mini.UTPageHitHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * UT 上报适配器，可以统计异常上报类型
 * */
class StatisticHelperAdapter {


    public static void customHit(String page, String action, Map<String, String> properties) {
        customHit(page, action, properties, 0);
    }


    public static void customHit(String page, String action, Map<String, String> properties, long time) {
        try {
            if (UTAnalytics.getInstance() == null || UTAnalytics.getInstance().getDefaultTracker() == null) {
                return;
            }
            if (page == null || action == null) {
                return;
            }
            if (properties == null) {
                properties = new HashMap<>();
            }
            UTControlHitBuilder lHitBuilder = new UTControlHitBuilder(page, action);
            lHitBuilder.setProperties(properties);
            UTAnalytics.getInstance().getDefaultTracker().send(lHitBuilder.build());
        } catch (Throwable e) {
        }
    }


    public static class UTControlHitBuilder extends UTHitBuilders.UTHitBuilder {
        public UTControlHitBuilder(@NonNull String aControlName) {
            if (TextUtils.isEmpty(aControlName)) {
                aControlName = PageNames.CRASH_DEFEND_PAGE;
            }
            String lPageName = UTPageHitHelper.getInstance().getCurrentPageName();
            if (TextUtils.isEmpty(lPageName)) {
                lPageName = PageNames.CRASH_DEFEND_CONTROL;
            }
            String lFullControlName = lPageName + "-" + aControlName;
            super.setProperty("_field_page", lPageName);
            super.setProperty("_field_event_id", "2101");
            super.setProperty("_field_arg1", lFullControlName);
        }

        public UTControlHitBuilder(@NonNull String aPageName, @NonNull String aControlName) {
            if (TextUtils.isEmpty(aControlName)) {
                aControlName = PageNames.CRASH_DEFEND_PAGE;
            }
            String lPageName = UTPageHitHelper.getInstance().getCurrentPageName();
            if (TextUtils.isEmpty(lPageName)) {
                lPageName = PageNames.CRASH_DEFEND_CONTROL;
            }
            String lFullControlName = getFullControlName(aPageName, aControlName);
            super.setProperty("_field_page", aPageName);
            super.setProperty("_field_event_id", "2101");
            super.setProperty("_field_arg1", lFullControlName);
        }

        public static String getFullControlName(String pageName, String controlName) {
            if (!controlName.startsWith(pageName)) {
                controlName = pageName + "-" + controlName;
            }
            return controlName;
        }
    }


}
