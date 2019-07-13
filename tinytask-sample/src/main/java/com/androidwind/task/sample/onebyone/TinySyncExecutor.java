package com.androidwind.task.sample.onebyone;

import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinySyncExecutor {

    private volatile static TinySyncExecutor sTinySyncExecutor;

    //store incoming task, waiting to put into ArrayBlockingQueue;
    private ArrayDeque<BaseSyncTask> pendingQueue = new ArrayDeque<>();
    private BaseSyncTask currentTask;

    private final AtomicInteger count = new AtomicInteger(1);

    public static TinySyncExecutor getInstance() {
        if (sTinySyncExecutor == null) {
            synchronized (TinySyncExecutor.class) {
                sTinySyncExecutor = new TinySyncExecutor();
            }
        }
        return sTinySyncExecutor;
    }

    private void coreExecute() {
        currentTask = pendingQueue.poll();
        if (currentTask != null) {
            System.out.println("[OneByOne]executing currentTask id = :" + currentTask.getId());
            TinyTaskExecutor.execute(new Task() {
                @Override
                public Object doInBackground() {
                    System.out.println("[OneByOne]doInBackground, " + "the current thread id = " + Thread.currentThread().getId());
                    return null;
                }

                @Override
                public void onSuccess(Object o) {
                    currentTask.doTask();
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        }
    }

    public void enqueue(final BaseSyncTask task) {
        task.setId(count.getAndIncrement());
        System.out.println("[OneByOne]The task id = :" + task.getId());
        pendingQueue.offer(task);//the ArrayDeque should not be blocked when operate offer
        System.out.println("[OneByOne]The pendingQueue size = :" + pendingQueue.size());
        if (currentTask == null) {
            coreExecute();
        }
    }

    public void finish() {
        System.out.println("[OneByOne]finish task, task id = " + currentTask.getId() + "; pendingQueue size = " + pendingQueue.size());
        coreExecute();
    }
}
