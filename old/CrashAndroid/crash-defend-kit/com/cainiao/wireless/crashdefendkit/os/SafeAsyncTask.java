package com.cainiao.wireless.crashdefendkit.os;

import android.os.AsyncTask;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;

public abstract class SafeAsyncTask<Params, Progress, Result> extends AsyncTask {


    protected boolean hasException = false;



    /**
     * safe do background task。
     * */
    @Override
    protected final Object doInBackground(Object[] objects) {
        try{
            return doInBackgroundSafe(objects);
        }catch (Exception e){
            CrashDefendKit.onCrash(SafeAsyncTask.class.getName(), e);
            hasException = true;
            return null;
        }
    }

    /**
     * safe do task on ui thread
     * */
    @Override
    protected final void onPostExecute(Object o) {
        try{
            if(hasException){
                return;
            }
            onPostExecuteSafe(o);
        }catch (Exception e){
            CrashDefendKit.onCrash(SafeAsyncTask.class.getName(), e);
        }
        super.onPostExecute(o);
    }



    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     *
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     *
     * @return A result, defined by the subclass of this task.
     * @see #onPostExecute
     */
    protected abstract Object doInBackgroundSafe(Object[] objects);


    /**
     * <p>Runs on the UI thread after {@link #doInBackgroundSafe}. The
     * specified result is the value returned by {@link #doInBackgroundSafe}.</p>
     *
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackgroundSafe}.
     *
     * @see #doInBackgroundSafe
     */
    protected abstract void onPostExecuteSafe(Object o);
}
