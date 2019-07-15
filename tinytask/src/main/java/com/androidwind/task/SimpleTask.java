package com.androidwind.task;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class SimpleTask implements Runnable {
    long SEQ; // the identity for task

    public String taskName;

    public Priority priority;

    public SimpleTask() {
    }

    public SimpleTask(Priority priority) {
        this.priority = priority;
    }
}
