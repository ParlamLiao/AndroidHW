package com.example.mac.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class frame extends AppCompatActivity {
    private Button toreturn;

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        setViewFlipper();
        toreturn = (Button) findViewById(R.id.button_return);
        toreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void setViewFlipper() {
        ViewFlipper mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);

        //添加要滚动的View
        mViewFlipper.addView(getImageView(R.drawable.pic_1));
        mViewFlipper.addView(getImageView(R.drawable.pic_2));
        mViewFlipper.addView(getImageView(R.drawable.pic_3));
        mViewFlipper.addView(getImageView(R.drawable.pic_4));
        mViewFlipper.addView(getImageView(R.drawable.pic_5));
        //设置开始和结束动画
        mViewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

        //设置间隔时间
        mViewFlipper.setFlipInterval(3000);

        //动画的监听
        mViewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始时
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束时
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //重复
            }
        });

        //开始轮播
        mViewFlipper.startFlipping();
    }

    private ImageView getImageView(int res) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(res);
        return imageView;
    }

  
}

