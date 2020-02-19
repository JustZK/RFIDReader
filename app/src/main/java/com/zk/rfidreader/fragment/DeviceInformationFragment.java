package com.zk.rfidreader.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.adapter.DeviceAdapter;
import com.zk.rfidreader.databinding.FragmentDeviceInformationBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DeviceInformationFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private final int REGISTER = 0x01;
    private final int HEART = 0x02;
    private final int VERSION = 0x03;
    private FragmentDeviceInformationBinding mFragmentDeviceInformationBinding;
    private DeviceAdapter mDeviceAdapter;
    private List<DeviceInformation> mDeviceInformationList = new ArrayList<>();

    private DeviceInformationFragmentHandler mHandler;

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case REGISTER:
            case HEART:
                DeviceInformation deviceInformation = (DeviceInformation) msg.obj;
                if (!mDeviceInformationList.contains(deviceInformation)) {
                    mFragmentDeviceInformationBinding.deviceInfoDeviceTv.setText("可用设备列表");
                    mDeviceInformationList.add(deviceInformation);
                    mDeviceAdapter.notifyDataSetChanged();
                }
                break;
            case VERSION:

                break;
        }
    }

    public static DeviceInformationFragment newInstance() {
        DeviceInformationFragment fragment = new DeviceInformationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentDeviceInformationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_device_information, container, false);
        mFragmentDeviceInformationBinding.setOnClickListener(this);
        mFragmentDeviceInformationBinding.deviceInfoLv.setOnItemClickListener(this);
        mDeviceAdapter = new DeviceAdapter(getActivity(), mDeviceInformationList);
        mFragmentDeviceInformationBinding.deviceInfoLv.setAdapter(mDeviceAdapter);


        UR880Entrance.getInstance().addOnDeviceInformationListener(mDeviceInformationListener);

        mHandler = new DeviceInformationFragmentHandler(this);
        return mFragmentDeviceInformationBinding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.Companion.getInstance().d("position = " + position);
    }

    private DeviceInformationListener mDeviceInformationListener = new DeviceInformationListener() {
        @Override
        public void registered(String deviceID, String deviceVersionNumber, String deviceRemoteAddress) {
            DeviceInformation deviceInformation = new DeviceInformation();
            deviceInformation.setDeviceID(deviceID);
            deviceInformation.setDeviceVersionNumber(deviceVersionNumber);
            deviceInformation.setDeviceRemoteAddress(deviceRemoteAddress);
            Message message = Message.obtain();
            message.obj = deviceInformation;
            message.what = REGISTER;
            mHandler.sendMessage(message);
        }

        @Override
        public void heartbeat(String deviceID) {
            DeviceInformation deviceInformation = new DeviceInformation();
            deviceInformation.setDeviceID(deviceID);
            Message message = Message.obtain();
            message.obj = deviceInformation;
            message.what = HEART;
            mHandler.sendMessage(message);
        }

        @Override
        public void versionInformation(String hardwareVersionNumber, String softwareVersionNumber, String firmwareVersionNumber) {

        }
    };

    private static class DeviceInformationFragmentHandler extends Handler {
        private final WeakReference<DeviceInformationFragment> deviceInformationFragmentWeakReference;

        DeviceInformationFragmentHandler(DeviceInformationFragment deviceInformationFragment) {
            super();
            deviceInformationFragmentWeakReference = new WeakReference<>(deviceInformationFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (deviceInformationFragmentWeakReference.get() != null) {
                deviceInformationFragmentWeakReference.get().handleMessage(msg);
            }
        }
    }
}
