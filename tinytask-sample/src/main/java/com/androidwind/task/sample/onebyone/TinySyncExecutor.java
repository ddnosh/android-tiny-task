package com.androidwind.task.sample.onebyone;

import com.androidwind.task.AdvancedTask;
import com.androidwind.task.SimpleTask;
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
    //1 capacity for executing task
//    private ArrayBlockingQueue<BaseSyncTask> executingQueue = new ArrayBlockingQueue<>(1);
    //current task
    private BaseSyncTask currentTask;

    private final AtomicInteger count = new AtomicInteger(1);

    public AtomicInteger getCount() {
        return count;
    }

    public TinySyncExecutor() {
        init();
    }

    public static TinySyncExecutor getInstance() {
        if (sTinySyncExecutor == null) {
            synchronized (TinySyncExecutor.class) {
                sTinySyncExecutor = new TinySyncExecutor();
            }
        }
        return sTinySyncExecutor;
    }

    private void init() {

        TinyTaskExecutor.execute(new SimpleTask() {
            @Override
            public Object doInBackground() {
                coreExecute();
                return null;
            }
        });
    }

    private void coreExecute() {
        currentTask = pendingQueue.poll();
        if (currentTask != null) {
            System.out.println("[OneByOne]executing currentTask id = :" + currentTask.getId());
//                executingQueue.put(currentTask);
            TinyTaskExecutor.execute(new AdvancedTask() {
                @Override
                public Object doInBackground() {
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
        System.out.println("[OneByOne]The task id = :" + getCount());
        pendingQueue.offer(task);//the ArrayDeque should not be blocked when operate offer
        System.out.println("[OneByOne]The pendingQueue size = :" + pendingQueue.size());
    }

    public void finish() {
        System.out.println("[OneByOne]finish task, task id = " + currentTask.getId());
//        executingQueue.remove(currentTask);
        coreExecute();
    }
}
