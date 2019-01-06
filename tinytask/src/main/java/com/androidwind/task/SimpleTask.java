package com.androidwind.task;

import com.androidwind.log.TinyLog;

/**
 * only handle task in background
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask<T> extends TaskCallable {


    /**
     * default priority is Process.THREAD_PRIORITY_DEFAULT;
     */
    public SimpleTask() {
        super();
    }

    public SimpleTask(int priority, String taskName) {
        super(priority, taskName);
    }

    public abstract T doInBackground();

    @Override
    public T call() throws Exception {//give exception to get().
        TinyLog.d( "compare: priority = " + getPriority() + ", taskName = " + getTaskName());
        final T t = doInBackground();
        return t;
    }

}
