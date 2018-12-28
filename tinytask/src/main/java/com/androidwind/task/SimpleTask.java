package com.androidwind.task;

import java.util.concurrent.Callable;

/**
 * only handle task in background
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask<T> implements Callable<T> {

    public abstract T doInBackground();

    @Override
    public T call() throws Exception {//give exception to get().
        final T t = doInBackground();
        return t;
    }
}
