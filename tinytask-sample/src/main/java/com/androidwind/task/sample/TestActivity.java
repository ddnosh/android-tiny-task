package com.androidwind.task.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestActivity extends Activity {

    private TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTextView = findViewById(R.id.textView);

        TinyTaskExecutor.execute(delayTask);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            Toast.makeText(TestActivity.this, "delay tinytask toast", Toast.LENGTH_SHORT).show();
            mTextView.setText("delayed!");
        }

        @Override
        public void onFail(Throwable throwable) {

        }
    };
}
