package com.androidwind.task;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyTaskExecutor<T> {

    private volatile static TinyTaskExecutor sTinyTaskExecutor;

    private ExecutorService mExecutor;
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private static HashMap<Callable, Runnable> sDelayTasks = new HashMap<>();

    //collect futuretask
    private static List futureList = new ArrayList<>();

    public static TinyTaskExecutor getInstance() {
        if (sTinyTaskExecutor == null) {
            synchronized (TinyTaskExecutor.class) {
                sTinyTaskExecutor = new TinyTaskExecutor();
            }
        }
        return sTinyTaskExecutor;
    }

    public TinyTaskExecutor() {
        mExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * add task
     *
     * @param callable
     * @param <T>
     */
    public static <T> void execute(Callable<T> callable) {
        execute(callable, 0);
    }

    public static <T> void execute(final Callable<T> callable, long delayMillisecond) {
        if (callable == null) return;
        if (delayMillisecond < 0) return;

        if (!getExecutor().isShutdown()) {
//        Future<T> future = getInstance().executor.submit(callable);

            if (delayMillisecond > 0) {
                Runnable delayRunnable = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (sDelayTasks) {
                            sDelayTasks.remove(callable);
                        }
                        realExecute(callable);
                    }
                };

                synchronized (sDelayTasks) {
                    sDelayTasks.put(callable, delayRunnable);
                }
                getMainThreadHandler().postDelayed(delayRunnable, delayMillisecond);
            } else {
                realExecute(callable);
            }
        }
    }

    private static <T> void realExecute(Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask<T>(callable);
        getExecutor().submit(futureTask);
        futureList.add(futureTask);
        System.out.println("[new] realExecute");
    }


    /**
     * remove task
     *
     * @param callable
     */
    public static <T> void removeTask(final Callable<T> callable) {
        if (callable == null) {
            return;
        }

        Runnable delayRunnable;
        synchronized (sDelayTasks) {
            delayRunnable = sDelayTasks.remove(callable);
        }

        if (delayRunnable != null) {
            getMainThreadHandler().removeCallbacks(delayRunnable);
        }

    }

    /**
     * check the future result, will block main thread, be careful.
     */
    public static void check() {
        for (Iterator it = futureList.iterator(); it.hasNext(); ) {
            FutureTask ft = (FutureTask) it.next();
            if (!ft.isDone()) {
                try {
                    //if use get(), you will block main thread util the sub thread finished, unless you need the result of sub thread.
                    System.out.println("the check result is: " + ft.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                it.remove();
            }
        }
    }

    private static ExecutorService getExecutor() {
        return getInstance().mExecutor;
    }

    public static Handler getMainThreadHandler() {
        return getInstance().mMainThreadHandler;
    }
}
