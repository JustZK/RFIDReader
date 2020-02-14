package com.zk.rfidreader.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.zk.rfidreader.R;
import com.zk.rfidreader.adapter.FragmentAdapter;
import com.zk.rfidreader.databinding.ActivityHomeBinding;
import com.zk.rfidreader.fragment.DeviceInformationFragment;
import com.zk.rfidreader.fragment.LabelInventoryFragment;
import com.zk.rfidreader.fragment.LabelOperationFragment;
import com.zk.rfidreader.fragment.LabelSettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding mActivityHomeBinding;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();
    }

    private void initView(){
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.device_info_tab_txt));
        titles.add(getString(R.string.label_inventory_tab_txt));
        titles.add(getString(R.string.label_operation_tab_txt));
        titles.add(getString(R.string.label_settings_tab_txt));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DeviceInformationFragment());
        fragments.add(new LabelInventoryFragment());
        fragments.add(new LabelOperationFragment());
        fragments.add(new LabelSettingsFragment());

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mActivityHomeBinding.honeViewPager.setAdapter(mFragmentAdapter);
        mActivityHomeBinding.honeTabs.setupWithViewPager(mActivityHomeBinding.honeViewPager);
    }

}