package com.androidwind.task;

/**
 * priority runnable
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class TaskRunnable extends BaseRunnable {
    public Priority priority;

    public Priority getPriority() {
        return priority;
    }

    public TaskRunnable() {
    }

    public TaskRunnable(Priority priority) {
        this.priority = priority;
    }
}
