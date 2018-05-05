package com.example.mac.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.Gravity;

public class linear1 extends AppCompatActivity {

    private Button toreturn;
    private Button bn_toHorizontal;
    private Button bn_toVertical;
    private Button bn_toLeft;
    private LinearLayout layout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear1);
        layout = (LinearLayout) findViewById(R.id.linearLayout);
        toreturn = (Button) findViewById(R.id.button_return);
        bn_toHorizontal = (Button) findViewById(R.id.bn_toHorizontal);
        bn_toVertical = (Button) findViewById(R.id.bn_toVertical);
        bn_toLeft = (Button) findViewById(R.id.bn_toLeft);

        toreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bn_toHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setOrientation(LinearLayout.HORIZONTAL);
            }
        });

        bn_toVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setOrientation(LinearLayout.VERTICAL);
            }
        });

        bn_toLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setGravity(Gravity.LEFT);
            }
        });
    }
}
