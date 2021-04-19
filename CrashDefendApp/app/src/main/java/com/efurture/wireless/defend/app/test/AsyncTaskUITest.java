package com.efurture.wireless.defend.app.test;

import android.os.AsyncTask;

public class AsyncTaskUITest extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        throw new RuntimeException("onPostExecute");
    }
}
