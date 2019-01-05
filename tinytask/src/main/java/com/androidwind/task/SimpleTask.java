package com.androidwind.task;

/**
 * only handle task in background
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask<T> extends TaskCallable {

    public SimpleTask() {
        super();
    }

    public SimpleTask(int priority) {
        super(priority);
    }

    public abstract T doInBackground();

    @Override
    public T call() throws Exception {//give exception to get().
        System.out.println("compare: priority = " + getPriority() + ", thread id = " + Thread.currentThread().getId());
        final T t = doInBackground();
        return t;
    }

}
