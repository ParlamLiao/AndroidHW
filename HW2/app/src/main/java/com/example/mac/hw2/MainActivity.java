package com.example.mac.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button linear = (Button) findViewById(R.id.button_linear);
        final Button relative = (Button) findViewById(R.id.button_relative);
        Button frame = (Button) findViewById(R.id.button_frame);
        Button table = (Button) findViewById(R.id.button_table);
        Button net = (Button) findViewById(R.id.button_net);

        linear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,linear1.class);
                startActivity(intent);
            }
        });
        relative.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,relative.class);
                startActivity(intent);
            }
        });
        frame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,frame.class);
                startActivity(intent);
            }
        });
        table.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,table.class);
                startActivity(intent);
            }
        });
        net.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,net.class);
                startActivity(intent);
            }
        });
    }

}
