package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class ExecutorFragment extends Fragment implements View.OnClickListener {

    public static ExecutorFragment newInstance() {
        ExecutorFragment fragment = new ExecutorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_executor, container, false);
        Button btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        Button btn2 = view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        return view;
    }

    private class ThreadPoolRunnable implements Runnable {

        private String name;

        public ThreadPoolRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized(this) {
                try{
                    System.out.println("[Test-Thread]" + Thread.currentThread().getName() + ", " + name);
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
                //if use LinkedBlockingQueue, the maximumPoolSize is unavailable,
                //if use ArrayBlockingQueue, the maximumPoolSize is available.
                ThreadPoolExecutor executor2 = new ThreadPoolExecutor(2, 6, 1, TimeUnit.DAYS, queue);
                for (int i = 0; i < 10; i++) {
//                    executor.execute(new Thread(new ThreadPoolRunnable(), "Test".concat(""+i)));
                    executor2.execute(new ThreadPoolRunnable("[Test-Runnable]".concat(""+i)));
                    int threadSize = queue.size();
                    System.out.println("[Test-Thread]线程队列大小为-->"+threadSize);
                }
                executor2.shutdown();
                break;
            case R.id.btn_2:
                test2();
                break;
        }
    }

    private void test2() {
        final ArrayBlockingQueue<String> valve = new ArrayBlockingQueue<>(10);
        Thread breadProductor = new Thread(new Runnable() {//productor
            @Override
            public void run() {
                while (true) {
                    String s = "[Test]one bread";
                    try {
                        valve.put(s);//blocking
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }// when the queue is full, the thread will be blocked util the queue isn't full
                    System.out.println("[Test]produced" + s);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, "[Test]Bread Producer");

        Thread breadConsumer = new Thread(new Runnable() {//consumer
            @Override
            public void run() {
                while (true) {
                    String s = null;
                    try {
                        s = valve.take();//when the queue is empty, the thread will be blocked util the blank queue comes a task
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[Test]consumed" + s);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        breadProductor.start();
        breadConsumer.start();

        Thread monitor = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    System.out.println("[Test]the left breads:" + valve.size());//monitor the breads
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        monitor.start();
    }
}