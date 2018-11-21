package com.shadow.viewinject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shadow.inject_lib.EventInject;
import com.shadow.inject_lib.ViewInject;
import com.shadow.inject_lib.ViewInjectUtil;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_main)
    TextView tv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjectUtil.register(this);
        tv_main.setText(".......");

    }

    @EventInject(values = {R.id.tv_main, R.id.tv_second, R.id.tv_third})
    public void onc(View v) {
        switch (v.getId()) {
            case R.id.tv_main:
                Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_second:
                Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_third:
                Toast.makeText(this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
