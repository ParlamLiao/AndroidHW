package com.example.mac.hw3_3;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText a;
    EditText b;
    EditText c;
    EditText d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取应用界面中的界面组件
        a = (EditText) findViewById(R.id.a);
        b = (EditText) findViewById(R.id.b);
        c= (EditText) findViewById(R.id.c);
        d = (EditText) findViewById(R.id.d);
        Button bn = (Button) findViewById(R.id.bn);
        // 为按钮绑定事件监听器
        bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View source) {
                // 获取系统的Configuration对象
                Configuration cfg = getResources().getConfiguration();
                String screen = cfg.orientation == Configuration.ORIENTATION_LANDSCAPE ?"横向屏幕": "竖向屏幕";
                String mncCode = cfg.mnc + "";
                String naviName = cfg.orientation == Configuration.NAVIGATION_NONAV ? "没有方向控制": cfg.orientation == Configuration.NAVIGATION_WHEEL ? "滚轮控制方向": cfg.orientation == Configuration.NAVIGATION_DPAD ? "方向键控制方向": "轨迹球控制方向";
                b.setText(naviName);
                String touchName = cfg.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "无触摸屏": "支持触摸屏";
                a.setText(screen);
                d.setText(mncCode);
                c.setText(touchName);
            }
        });
    }
}
