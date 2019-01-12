package com.androidwind.task;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class ComparableFutureTask extends FutureTask implements Comparable<ComparableFutureTask> {

    private Callable callable;

    public Callable getCallable() {
        return callable;
    }

    @Override
    public int compareTo(ComparableFutureTask second) {
        // 时间越小越优先
        System.out.println("[ComparableFutureTask] compare: this.priority = " + ((TaskCallable) getCallable()).getPriority()
                + ", this.taskName = " + ((TaskCallable) getCallable()).getTaskName()
                + ", second.priority = " + ((TaskCallable) second.getCallable()).getPriority()
                + ", second.taskName = " + ((TaskCallable) second.getCallable()).getTaskName());
        if (((TaskCallable) getCallable()).getPriority() < ((TaskCallable) second.getCallable()).getPriority()) {
            return -1;
        } else if (((TaskCallable) getCallable()).getPriority() > ((TaskCallable) second.getCallable()).getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }

    public ComparableFutureTask(@NonNull Callable callable) {
        super(callable);
        this.callable = callable;
    }
}
