package com.example.mac.soundrecorder_final;

import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by mac on 14/5/18.
 */

public class Recorder {
    File soundFile;
    MediaRecorder mRecorder;
    boolean status;
    SimpleDateFormat formatter=   new SimpleDateFormat("HH时mm分");
    int counter=1;
    Recorder(){
        this.status=false;
    }
    public void onChange(TextView inform){
        this.status=!(this.status);
        System.out.println("第"+(counter++)+"次调用");
        if(status==true){
            try{
                Date date=new Date(System.currentTimeMillis());
                String str    =formatter.format(date);
                soundFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getCanonicalFile()+"/录音"+"sound"+str+".amr");
                System.out.println(soundFile);
                mRecorder=new MediaRecorder();
                System.out.println("mRecorder=new MediaRecorder(); FINISH");
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                System.out.println("mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); FINISH");
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                System.out.println("mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT); FINISH");
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                System.out.println("mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); FINISH");
                mRecorder.setOutputFile(soundFile.getAbsolutePath());
                System.out.println("mRecorder.setOutputFile(soundFile.getAbsolutePath()); FINISH");
                mRecorder.prepare();
                System.out.println("mRecorder.prepare(); FINISH");
                mRecorder.start();
                System.out.println("mRecorder.start(); FINISH");
                inform.setText("录音功能启动");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            if(soundFile!=null &&soundFile.exists()){
                mRecorder.reset();
                System.out.println(" mRecorder.reset(); FINISH");
                mRecorder.release();
                System.out.println("  mRecorder.release(); FINISH");
                mRecorder=null;
                System.out.println(" mRecorder=null; FINISH");
                inform.setText("录音功能关闭");
            }

        }
    }
}
