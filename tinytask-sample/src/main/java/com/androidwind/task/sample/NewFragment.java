package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidwind.task.SimpleTask;
import com.androidwind.task.Priority;
import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

/**
 * v2.x
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class NewFragment extends Fragment implements View.OnClickListener {

    public static NewFragment newInstance() {
        NewFragment fragment = new NewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_new, container, false);

        Button btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);

        Button btn2 = view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);

        Button btn3 = view.findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);

        Button btn4 = view.findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);

        Button btn5 = view.findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);

        Button btn6 = view.findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);

        Button btn7 = view.findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);

        Button btn8 = view.findViewById(R.id.btn_8);
        btn8.setOnClickListener(this);

        Button btn9 = view.findViewById(R.id.btn_9);
        btn9.setOnClickListener(this);

        System.out.println("[new] thread id in main: " + Thread.currentThread().getId());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Toast.makeText(getActivity(), "this is a toast, you know.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_2:
                TinyTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("[new] thread id in tinytask: " + Thread.currentThread().getId() +
                                ", is main thread:" + TinyTaskExecutor.isMainThread());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[new] no callback after 5 sec");
                    }
                });
                break;
            case R.id.btn_3:
                TinyTaskExecutor.execute(new Task<String>() { //default priority is Priority.LOW
                    @Override
                    public String doInBackground() {
                        System.out.println("[new] thread id in tinytask: " + Thread.currentThread().getId());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[new] with callback after 5 sec");
                        return "task with sleep 5 sec";
                    }

                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }
                });
                break;
            case R.id.btn_4:
                TinyTaskExecutor.execute(new Task<String>(Priority.NORMAL) {
                    @Override
                    public String doInBackground() {
                        System.out.println("[new] thread id in tinytask: " + Thread.currentThread().getId());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[new] with callback after 5 sec");
                        return "task with sleep 5 sec";
                    }

                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }
                });
                break;
            case R.id.btn_5:
                System.out.println("[new] with task delay");
                TinyTaskExecutor.execute(delayTask, 5000);
                break;
            case R.id.btn_6:
                System.out.println("[new] with remove delay task");
                TinyTaskExecutor.removeTask(delayTask);
                break;
            case R.id.btn_7:
                System.out.println("[new] post to main thread");
                TinyTaskExecutor.postToMainThread(delayRunnable, 2000);
                break;
            case R.id.btn_8:
                System.out.println("[new] remove post to main thread");
                TinyTaskExecutor.removeMainThreadRunnable(delayRunnable);
                break;
            case R.id.btn_9:
                System.out.println("[new] priority test");
                for (int i = 0; i < 50; i++) {
                    SimpleTask priorityRunnable;
                    if (i % 3 == 1) {
                        priorityRunnable = new SimpleTask(Priority.HIGH) {

                            @Override
                            public void run() {
                                System.out.println("[new] " + Thread.currentThread().getName() + "Priority.HIGH");
                            }
                        };
                    } else if (i % 5 == 0) {
                        priorityRunnable = new SimpleTask(Priority.LOW) {

                            @Override
                            public void run() {
                                System.out.println("[new] " + Thread.currentThread().getName() + "Priority.LOW");
                            }
                        };
                    } else {
                        priorityRunnable = new SimpleTask(Priority.NORMAL) {

                            @Override
                            public void run() {
                                System.out.println("[new] " + Thread.currentThread().getName() + "Priority.NORMAL");
                            }
                        };
                    }
                    TinyTaskExecutor.execute(priorityRunnable);
                }
                break;
        }
    }

    private Task<String> delayTask = new Task<String>() {
        @Override
        public String doInBackground() {
            System.out.println("[new] thread id in tinytask: " + Thread.currentThread().getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[new] with callback after 5 sec");
            return "task with sleep 5 sec";
        }

        @Override
        public void onSuccess(String s) {
            Toast.makeText(getActivity(), "delay tinytask toast", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFail(Throwable throwable) {

        }
    };

    private Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getActivity(), "post to main thread toast", Toast.LENGTH_SHORT).show();
        }
    };

    class TestTask extends SimpleTask {
        public TestTask(Priority priority) {
            super(priority);
        }

        @Override
        public void run() {
            System.out.println("[new] " + Thread.currentThread().getName() + priority);
        }
    }
}
