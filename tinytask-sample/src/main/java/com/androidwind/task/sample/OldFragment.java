package com.androidwind.task.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class OldFragment extends Fragment implements View.OnClickListener {

    private TextView tvResult;
    private int result;

    public static OldFragment newInstance() {
        OldFragment fragment = new OldFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_old, container, false);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText("OldFragment");

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

        tvResult = view.findViewById(R.id.tv_result);

        return view;
    }

    //  init1---------------------------------------------------------------------------------------
    private void init1() {
        System.out.println("[old] thread id in main: " + Thread.currentThread().getId());
        new Thread(mRunnable1).start();
        new Thread(mRunnable1).start();
    }

    private Runnable mRunnable1 = new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("[old] thread id in run: " + Thread.currentThread().getId());
                int result = 0;
                int i = 0;
                for (; i < 500; i++) {
                    result += i;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("[old] result = " + result);
                Message message = mHandler.obtainMessage();
                message.what = 0;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }
    };

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Toast.makeText(getContext(), "old toast: " + msg.obj, Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                Toast.makeText(getContext(), "old toast: " + msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };

    //  init2---------------------------------------------------------------------------------------
    private void init2() {
        System.out.println("[old] thread id in main: " + Thread.currentThread().getId());
        new Thread() {
            @Override
            public void run() {
                System.out.println("[old] thread id in run: " + Thread.currentThread().getId());
                try {
                    Thread.sleep(5000);// simulate time-consuming task;
                    result = 222;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(mRunnable2);
            }
        }.start();

    }

    Runnable mRunnable2 = new Runnable() {
        @Override
        public void run() {
            System.out.println("[old] thread id in runnable: " + Thread.currentThread().getId());
//            Message message = mHandler.obtainMessage();
//            message.what = 1;
//            message.obj = 999;
//            mHandler.sendMessage(message);
            tvResult.setText("view updated, and the result is " + result);
        }
    };

    //  init3---------------------------------------------------------------------------------------
    private void init3() {
        HandlerThread myHandlerThread = new HandlerThread("handler-thread");
        myHandlerThread.start();
        Handler handler = new Handler(myHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //这个方法是运行在 handler-thread 线程中的 ，可以执行耗时操作
                Log.d("handler ", "消息： " + msg.what + "  线程： " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = mHandler.obtainMessage();
                message.what = 1;
                message.obj = 333;
                mHandler.sendMessage(message);
            }
        };

        //在主线程给handler发送消息
        handler.sendEmptyMessage(1);
    }

    //  init4---------------------------------------------------------------------------------------
    private void init4() {
        HandlerThread myHandlerThread = new HandlerThread("handler-thread");
        myHandlerThread.start();
        Handler handler = new Handler(myHandlerThread.getLooper());
        handler.post(mRunnable4);
    }

    private Runnable mRunnable4 = new Runnable() {
        @Override
        public void run() {
            //这个方法是运行在 handler-thread 线程中的 ，可以执行耗时操作
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = mHandler.obtainMessage();
            message.what = 1;
            message.obj = 444;
            mHandler.sendMessage(message);
        }
    };

    @Override
    public void onClick(View v) {
        tvResult.setText("");
        switch (v.getId()) {
            case R.id.btn_1:
                init1();
                break;
            case R.id.btn_2:
                init2();
                break;
            case R.id.btn_3:
                init3();
                break;
            case R.id.btn_4:
                init4();
                break;
            case R.id.btn_5:
                Toast.makeText(getActivity(), "this is a toast, you know.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable2);
    }
}
