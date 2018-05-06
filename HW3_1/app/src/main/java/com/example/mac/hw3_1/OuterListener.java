package com.example.mac.hw3_1;

import android.view.View;
import android.widget.EditText;

/**
 * Created by mac on 6/5/18.
 */

public class OuterListener implements View.OnClickListener {
    EditText editText;

    public OuterListener(EditText text){
        editText = text;
    }
    @Override
    public void onClick(View view){
        editText.setText("外部类");
    }
}