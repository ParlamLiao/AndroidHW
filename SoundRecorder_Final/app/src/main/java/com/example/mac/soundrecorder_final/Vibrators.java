package com.example.mac.soundrecorder_final;



import android.content.Context;
import android.os.Vibrator;

public class Vibrators {
    Vibrator mVibrator;
    public Context context;
    public Vibrators(MainActivity c){
        context=c;
    }
    public void start(){
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void vib(long[] pattern,int repeat){
        mVibrator.vibrate(pattern,repeat);
    }
    public void stop(){
        mVibrator.cancel();
    }
}
