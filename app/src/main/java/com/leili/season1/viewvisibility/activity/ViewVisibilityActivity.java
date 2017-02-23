package com.leili.season1.viewvisibility.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.leili.season1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewGroup内嵌View的可见性
 * Created by lei on 6/28/16.
 */

public class ViewVisibilityActivity extends Activity {
    private ViewGroup outer;
    private View innerA;
    private Button btnVisible, btnInvisible, btnGone;
    private TextView tv;

    List<String> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_visibility_activity);
        initViews();
    }

    private void initViews() {
        outer = (ViewGroup) findViewById(R.id.outer);
        innerA = outer.findViewById(R.id.btnA);
        btnVisible = (Button) findViewById(R.id.setVisible);
        btnInvisible = (Button) findViewById(R.id.setInvisible);
        btnGone = (Button) findViewById(R.id.setGone);
        btnVisible.setOnClickListener(onClickListener);
        btnInvisible.setOnClickListener(onClickListener);
        btnGone.setOnClickListener(onClickListener);
        tv= (TextView) findViewById(R.id.info);
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnVisible) {
                outer.setVisibility(View.VISIBLE);
            } else if (v == btnInvisible) {
                outer.setVisibility(View.INVISIBLE);
            } else {
                outer.setVisibility(View.GONE);
            }
            info.add(visibilityInfo());
            updateText();
        }
    };
    private String visibilityInfo() {
        return ("outer visibility: " + outer.getVisibility() + "; inner visibility: " + innerA.getVisibility() + "; inner isShown: " + innerA.isShown());
    }

    private void updateText() {
        StringBuilder sb = new StringBuilder();
        for (String s : info) {
            sb.append(s);
            sb.append("\n");
        }
        tv.setText(sb.toString());
    }
}
