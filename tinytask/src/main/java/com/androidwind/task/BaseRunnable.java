package com.androidwind.task;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseRunnable implements Runnable {

    long SEQ; // the identity for task

    private String taskName;

    public String getTaskName() {
        return taskName;
    }
}
