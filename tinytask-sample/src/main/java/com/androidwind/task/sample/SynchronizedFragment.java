package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidwind.task.TaskCallable;
import com.androidwind.task.TinyTaskExecutor;

import java.util.ArrayDeque;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SynchronizedFragment extends Fragment implements View.OnClickListener {

    public static SynchronizedFragment newInstance() {
        SynchronizedFragment fragment = new SynchronizedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_synchronized, container, false);
        Button btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        System.out.println("[new] thread id in main: " + Thread.currentThread().getId());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                test();
                break;
        }
    }

//    private void test() {
//        for (int i = 0; i < 10; i++) {
//            execute(new SimpleTask<String>(i + "") {
//                @Override
//                public String doInBackground() {
//                    try {
//                        System.out.println("The taskName is :" + getTaskName());
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return "simple task with sleep 1 sec";
//                }
//            });
//        }
//    }
//
//    private TaskCallable mActive;
//    private ArrayDeque<TaskCallable> mArrayDeque = new ArrayDeque<>();
//
//    public synchronized void execute(final SimpleTask task) {
//        mArrayDeque.offer(new TaskCallable() {
//            @Override
//            public Object call() throws Exception {
//                try {
//                    task.call();
//                } finally {
//                    scheduleNext();
//                }
//                return null;
//            }
//        });
//        // 第一次入队列时mActive为空，因此需要手动调用scheduleNext方法
//        if (mActive == null) {
//            scheduleNext();
//        }
//    }
//
//    private void scheduleNext() {
//        if ((mActive = mArrayDeque.poll()) != null) {
//            TinyTaskExecutor.execute(mActive);
//        }
//    }

    private void test() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("The taskName is :" + (j + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private TaskCallable mActive;
    private ArrayDeque<TaskCallable> mArrayDeque = new ArrayDeque<>();

    public synchronized void execute(final Runnable r) {
        mArrayDeque.offer(new TaskCallable() {
            @Override
            public Object call() throws Exception {
                try {
                    r.run();
                } finally {
                    scheduleNext();
                }
                return null;
            }
        });
        // 第一次入队列时mActive为空，因此需要手动调用scheduleNext方法
        if (mActive == null) {
            scheduleNext();
        }
    }

    private void scheduleNext() {
        if ((mActive = mArrayDeque.poll()) != null) {
            TinyTaskExecutor.execute(mActive);
        }
    }

}
