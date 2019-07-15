package com.androidwind.task;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseTask implements Runnable {
    long SEQ; // the identity for task

    public String taskName;

    public Priority priority;

    public BaseTask() {
    }

    public BaseTask(Priority priority) {
        this.priority = priority;
    }
}
