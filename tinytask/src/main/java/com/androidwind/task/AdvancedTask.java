package com.androidwind.task;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class AdvancedTask<T> extends TaskCallable {

    public AdvancedTask() {
    }

    public AdvancedTask(int priority) {
        super(priority);
    }

    public AdvancedTask(int priority, String taskName) {
        super(priority, taskName);
    }

    public abstract T doInBackground();

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable throwable);

    @Override
    public T call() throws Exception {//give exception to get().
        System.out.println("[AdvancedTask] compare: priority = " + getPriority() + ", taskName = " + getTaskName());
        try {
            final T t = doInBackground();
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(t);
                }
            });
            return t;
        } catch (final Throwable throwable) {
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onFail(throwable);
                }
            });
            return null;
        }
    }
}
