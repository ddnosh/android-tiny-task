package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment mOldFragment, mNewFragment, mExecutorFragment, mSynchronizedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn_fragment1);
        btn1.setOnClickListener(this);

        Button btn2 = findViewById(R.id.btn_fragment2);
        btn2.setOnClickListener(this);

        Button btn3 = findViewById(R.id.btn_fragment3);
        btn3.setOnClickListener(this);

        Button btn4 = findViewById(R.id.btn_fragment4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment1:
                updateOldFragment();
                break;
            case R.id.btn_fragment2:
                updateNewFragment();
                break;
            case R.id.btn_fragment3:
                updateExecutorFragment();
                break;
            case R.id.btn_fragment4:
                updateSynchronizedFragment();
                break;
        }
    }

    private void updateOldFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mOldFragment == null) {
            mOldFragment = OldFragment.newInstance();
        }
        ft.replace(R.id.fragment_container, mOldFragment);
        ft.commit();
    }

    private void updateNewFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mNewFragment == null) {
            mNewFragment = NewFragment.newInstance();
        }
        ft.replace(R.id.fragment_container, mNewFragment);
        ft.commit();
    }

    private void updateExecutorFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mExecutorFragment == null) {
            mExecutorFragment = ExecutorFragment.newInstance();
        }
        ft.replace(R.id.fragment_container, mExecutorFragment);
        ft.commit();
    }

    private void updateSynchronizedFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mExecutorFragment == null) {
            mExecutorFragment = SynchronizedFragment.newInstance();
        }
        ft.replace(R.id.fragment_container, mExecutorFragment);
        ft.commit();
    }
}
