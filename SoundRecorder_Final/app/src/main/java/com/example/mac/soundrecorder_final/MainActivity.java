package com.example.mac.soundrecorder_final;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity{
    public Recorder recorder= new Recorder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView inform=(TextView)findViewById(R.id.inform);
        ShakeListener shakeListener=new ShakeListener(this);
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener(){
            public void onShake(){
                recorder.onChange(inform);
            }
        });
    }
}

