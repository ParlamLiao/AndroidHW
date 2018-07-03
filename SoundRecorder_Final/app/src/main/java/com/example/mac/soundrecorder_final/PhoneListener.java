package com.example.mac.soundrecorder_final;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;

/**
 * Created by mac on 17/5/18.
 */

public class PhoneListener extends PhoneStateListener {
    private TelephonyManager telephonyManager;
    private Context context;
    private OnPhoneListener onPhoneListener;
    private boolean BeCalled=false;

    public interface OnPhoneListener{
        void onPhone();
    }

    public void setOnPhoneListener(OnPhoneListener listener){
        onPhoneListener=listener;
    }


    public PhoneListener(MainActivity c){
        context=c;
    }
    public void start(){
        telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager != null){
            telephonyManager.listen(this,PhoneStateListener.LISTEN_CALL_STATE);
             System.out.println("电话监听器创建成功");
    }
        else{
            System.out.println("电话监听器创建失败");
        }
    }
    public void stop(){
        telephonyManager.listen(this,PhoneStateListener.LISTEN_NONE);

    }
    @Override
    public void onCallStateChanged(int state,String incomingNumber){
        try{
            switch (state) {

                case TelephonyManager.CALL_STATE_IDLE: //无任何状态时
                    if(BeCalled) {
                        onPhoneListener.onPhone();
                        System.out.println("检测到挂断");
                    }
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK: //接起电话时
                    onPhoneListener.onPhone();
                    System.out.println("检测到通话");
                    BeCalled=true;
                    break;

                default:

                    break;

            }

        }catch (Exception e) {

            e.printStackTrace();

        }
    }
}
