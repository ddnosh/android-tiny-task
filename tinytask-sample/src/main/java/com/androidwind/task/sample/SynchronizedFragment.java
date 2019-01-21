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
import java.util.concurrent.ArrayBlockingQueue;

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
        Button btn2 = view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                test1();
                break;
            case R.id.btn_2:
                test2();
                break;
        }
    }

//    private void test1() {
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

    //----------------------------------------------------------------------------------------------
    private void test1() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            execute1(new Runnable() {
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

    public synchronized void execute1(final Runnable r) {
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
    //----------------------------------------------------------------------------------------------

    final ArrayBlockingQueue<Runnable> valve = new ArrayBlockingQueue<>(1);

    private void test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    final int j = i;
                    execute2(new Runnable() {
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
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Runnable s = null;
                    try {
                        s = valve.take();//when the queue is empty, the thread will be blocked util the blank queue comes a task
                        s.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public synchronized void execute2(final Runnable r) {
        try {
            valve.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
