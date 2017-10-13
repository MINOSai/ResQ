package com.resq.resqserviceapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Created by minos.ai on 03/07/17.
 */

public class Tab2Stabilizer extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2stabilizer, container, false);
        TextView textView = rootView.findViewById(R.id.stabilizerOilCooledContent);
        textView.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }
}
