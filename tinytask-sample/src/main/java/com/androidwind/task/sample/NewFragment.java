package com.androidwind.task.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class NewFragment extends Fragment {

    public static NewFragment newInstance() {
        NewFragment fragment = new NewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_new, container, false);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText("NewFragment");
        return view;
    }
}
