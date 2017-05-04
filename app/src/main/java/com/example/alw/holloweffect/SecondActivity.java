package com.example.alw.holloweffect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.alw.holloweffect.view.DialogLayout;


/**
 * Created by alw on 2017/4/6.
 */

public class SecondActivity extends Activity implements View.OnClickListener {


    private DialogLayout dialogLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        dialogLayout = (DialogLayout) findViewById(R.id.dialogview);
    }

    @Override
    public void onClick(View v) {

    }
}
