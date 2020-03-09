package com.zk.rfidreader.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.adapter.FragmentAdapter;
import com.zk.rfidreader.databinding.ActivityHomeBinding;
import com.zk.rfidreader.fragment.DeviceInformationFragment;
import com.zk.rfidreader.fragment.LabelInventoryFragment;
import com.zk.rfidreader.fragment.LabelOperationFragment;
import com.zk.rfidreader.fragment.LabelSettingsFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final static int CONNECT = 0x01;

    private ActivityHomeBinding mActivityHomeBinding;
    private FragmentAdapter mFragmentAdapter;
    private String mDeviceID;

    private HomeActivityHandler homeActivityHandler;
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case CONNECT:
                UR880Entrance.getInstance().connect();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();

        homeActivityHandler = new HomeActivityHandler(this);
        homeActivityHandler.sendEmptyMessageDelayed(CONNECT, 1000);
    }

    private void initView(){
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.device_info_tab_txt));
        titles.add(getString(R.string.label_inventory_tab_txt));
//        titles.add(getString(R.string.label_operation_tab_txt));
        titles.add(getString(R.string.label_settings_tab_txt));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DeviceInformationFragment.newInstance());
        fragments.add(LabelInventoryFragment.newInstance());
//        fragments.add(LabelOperationFragment.newInstance());
        fragments.add(LabelSettingsFragment.newInstance());

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mActivityHomeBinding.honeViewPager.setAdapter(mFragmentAdapter);
        mActivityHomeBinding.honeTabs.setupWithViewPager(mActivityHomeBinding.honeViewPager);
    }

    private static class HomeActivityHandler extends Handler {
        private final WeakReference<HomeActivity> homeActivityWeakReference;

        HomeActivityHandler(HomeActivity homeActivity) {
            super();
            homeActivityWeakReference = new WeakReference<>(homeActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (homeActivityWeakReference.get() != null) {
                homeActivityWeakReference.get().handleMessage(msg);
            }
        }
    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(String deviceID) {
        mDeviceID = deviceID;
    }

    @Override
    protected void onDestroy() {
        UR880Entrance.getInstance().disConnect();
        super.onDestroy();
    }
}