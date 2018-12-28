package com.androidwind.task;

import java.util.concurrent.Callable;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class Task<T> implements Callable<T> {

    public abstract T doInBackground();

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable throwable);

    @Override
    public T call() throws Exception{//give exception to get().
        try {
            final T t = doInBackground();
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(t);
                }
            });
            return t;
        } catch (final Throwable throwable) {
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onFail(throwable);
                }
            });
        } finally {
            return null;
        }
    }
}
