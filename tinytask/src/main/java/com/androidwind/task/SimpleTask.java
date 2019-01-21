package com.androidwind.task;

/**
 * only handle task in background
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask<T> extends TaskCallable {

    private final static String TAG = "SimpleTask";

    /**
     * default priority is Process.THREAD_PRIORITY_DEFAULT;
     */
    public SimpleTask() {
        super();
    }

    public SimpleTask(int priority) {
        super(priority);
    }

    public SimpleTask(String taskName) {
        super(taskName);
    }

    public SimpleTask(int priority, String taskName) {
        super(priority, taskName);
    }

    public abstract T doInBackground();

    @Override
    public T call() throws Exception {//give exception to get().
        System.out.println("[SimpleTask] compare: priority = " + getPriority() + ", taskName = " + getTaskName());
        final T t = doInBackground();
        return t;
    }

}
