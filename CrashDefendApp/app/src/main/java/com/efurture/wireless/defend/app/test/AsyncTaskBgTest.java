package com.efurture.wireless.defend.app.test;

import android.os.AsyncTask;

public class AsyncTaskBgTest extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {
        throw new RuntimeException("doInBackground");
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
