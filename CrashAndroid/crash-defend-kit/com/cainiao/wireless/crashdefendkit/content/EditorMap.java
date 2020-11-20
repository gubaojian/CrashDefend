package com.cainiao.wireless.crashdefendkit.content;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 合并SharePreference的修改，多次apply到一次，减少文件读写。
 * */
public class EditorMap {


    private Map<String, Object> mPreferencesMap;

    public EditorMap(){
        mPreferencesMap = new HashMap<>(8);
    }
    /**
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.  Passing {@code null}
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putString(String key, String value){
        mPreferencesMap.put(key, value);
        return this;
    }

    /**
     * @param key The name of the preference to modify.
     * @param values The set of new values for the preference.  Passing {@code null}
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putStringSet(String key, Set<String> values){
        mPreferencesMap.put(key, values);
        return this;
    }

    /**
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putInt(String key, int value){
        mPreferencesMap.put(key, value);
        return this;
    }

    /**
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putLong(String key, long value){
        mPreferencesMap.put(key, value);
        return this;
    }

    /**
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putFloat(String key, float value){
        mPreferencesMap.put(key, value);
        return this;
    }

    /**
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public EditorMap putBoolean(String key, boolean value){
        mPreferencesMap.put(key, value);
        return this;
    }


    /**
     * commit to SharePreferences Editor
     * */
    public SharedPreferences.Editor toSharePreferences(SharedPreferences.Editor editor){
        if(mPreferencesMap == null || mPreferencesMap.size() == 0){
            return editor;
        }
        Set<Map.Entry<String,Object>> entries = mPreferencesMap.entrySet();
        for(Map.Entry<String,Object> entry : entries){
            if(entry.getValue() instanceof String){
                editor.putString(entry.getKey(), (String) entry.getValue());
            }else if(entry.getValue() instanceof Integer){
                editor.putInt(entry.getKey(), (Integer) entry.getValue());
            }else if(entry.getValue() instanceof Boolean){
                editor.putBoolean(entry.getKey(), (Boolean) entry.getValue());
            }else if(entry.getValue() instanceof Float){
                editor.putFloat(entry.getKey(), (Float) entry.getValue());
            }else if(entry.getValue() instanceof Long){
                editor.putLong(entry.getKey(), (Long) entry.getValue());
            }else if(entry.getValue() instanceof Set){
                editor.putStringSet(entry.getKey(), (Set<String>) entry.getValue());
            }
        }
        return editor;
    }


    /**
     * 返回所有数据
     * */
    public Map<String, Object> getPreferencesMap() {
        return mPreferencesMap;
    }
}
