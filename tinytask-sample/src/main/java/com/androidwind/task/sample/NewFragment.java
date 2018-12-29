package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidwind.task.SimpleTask;
import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

/**
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
                TinyTaskExecutor.check();
                break;
            case R.id.btn_3:
                TinyTaskExecutor.execute(new SimpleTask<String>() {
                    @Override
                    public String doInBackground() {
                        System.out.println("[new] thread id in tinytask: " + Thread.currentThread().getId());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[new] no callback after 5 sec");
                        return "simple task with sleep 5 sec";
                    }
                });
                break;
            case R.id.btn_4:
                TinyTaskExecutor.execute(new Task<String>() {
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
                        Toast.makeText(getActivity(), "tinytask toast", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }
                });
                break;
            case R.id.btn_5:
                System.out.println("[new] with task delay");
                TinyTaskExecutor.execute(task, 5000);
                break;
            case R.id.btn_6:
                System.out.println("[new] with remove delay task");
                TinyTaskExecutor.removeTask(task);
                break;
            case R.id.btn_7:
                System.out.println("[new] post to main thread");
                TinyTaskExecutor.postToMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "post to main thread toast", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
                break;
        }
    }

    private Task<String> task = new Task<String>() {
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
}
