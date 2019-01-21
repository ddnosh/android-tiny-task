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
    private String taskName;

    public int getPriority() {
        return priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskCallable() {
    }

    public TaskCallable(int priority) {
        this.priority = priority;
    }

    public TaskCallable(String taskName) {
        this.taskName = taskName;
    }

    public TaskCallable(int priority, String taskName) {
        this.priority = priority;
        this.taskName = taskName;
    }

}
