package com.zk.rfidreader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.serial.ur880.UR880SerialOperation;
import com.zk.rfidreader.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
