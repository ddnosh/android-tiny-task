package com.androidwind.task;

import java.util.concurrent.Callable;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

/**
 * base callable
 */
public abstract class TaskCallable implements Callable {
    private int priority;

    public int getPriority() {
        return priority;
    }

    public TaskCallable() {
    }

    public TaskCallable(int priority) {
        this.priority = priority;
    }

}
