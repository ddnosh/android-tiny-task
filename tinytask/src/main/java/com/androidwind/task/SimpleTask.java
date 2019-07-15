package com.androidwind.task;

/**
 * run in background
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask<T> extends BaseTask {
    public SimpleTask() {
    }

    public SimpleTask(Priority priority) {
        super(priority);
    }

}
