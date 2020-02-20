package com.zk.rfidreader.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.activity.HomeActivity;
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

    private FragmentDeviceInformationBinding mBinding;
    private DeviceAdapter mDeviceAdapter;
    private List<DeviceInformation> mDeviceInformationList = new ArrayList<>();
    private DeviceInformation mDeviceInformation;
    private ProgressDialog mGetVersionProgressDialog;

    private DeviceInformationFragmentHandler mHandler;

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case REGISTER:
            case HEART:
                DeviceInformation deviceInformation = (DeviceInformation) msg.obj;
                if (!mDeviceInformationList.contains(deviceInformation)) {
                    mBinding.deviceInfoDeviceTv.setText("可用设备列表");
                    mDeviceInformationList.add(deviceInformation);
                    mDeviceAdapter.notifyDataSetChanged();
                }
                break;
            case VERSION:
                if (mGetVersionProgressDialog != null && mGetVersionProgressDialog.isShowing()) {
                    mGetVersionProgressDialog.dismiss();
                }
                Bundle bundle = msg.getData();
                mDeviceInformation.setHardwareVersionNumber(bundle.getString("hardwareVersionNumber"));
                mDeviceInformation.setSoftwareVersionNumber(bundle.getString("softwareVersionNumber"));
                mDeviceInformation.setFirmwareVersionNumber(bundle.getString("firmwareVersionNumber"));
                deviceShow(mDeviceInformation);
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

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_device_information, container, false);
        mBinding.setOnClickListener(this);
        mBinding.deviceInfoLv.setOnItemClickListener(this);
        mDeviceAdapter = new DeviceAdapter(getActivity(), mDeviceInformationList);
        mBinding.deviceInfoLv.setAdapter(mDeviceAdapter);


        UR880Entrance.getInstance().addOnDeviceInformationListener(mDeviceInformationListener);

        mHandler = new DeviceInformationFragmentHandler(this);
        return mBinding.getRoot();
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.Companion.getInstance().d("position = " + position);
        mDeviceInformation = mDeviceInformationList.get(position);
        ((HomeActivity) getActivity()).setDeviceID(mDeviceInformation.getDeviceID());
        deviceShow(mDeviceInformation);
        if (mGetVersionProgressDialog == null) {
            mGetVersionProgressDialog = new ProgressDialog(getContext());
            mGetVersionProgressDialog.setMessage("正在获取读写器详细信息...");
        }
        mGetVersionProgressDialog.show();
        UR880Entrance.getInstance().send(
                new UR880SendInfo.Builder().getVersionInformation(mDeviceInformation.getDeviceID()).build());
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
        public void versionInformation(String hardwareVersionNumber, String softwareVersionNumber,
                                       String firmwareVersionNumber) {
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("hardwareVersionNumber", hardwareVersionNumber);
            bundle.putString("softwareVersionNumber", softwareVersionNumber);
            bundle.putString("firmwareVersionNumber", firmwareVersionNumber);
            message.setData(bundle);
            message.what = VERSION;
            mHandler.sendMessage(message);
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

    private void deviceShow(DeviceInformation deviceInformation) {
        if (!TextUtils.isEmpty(deviceInformation.getDeviceID())) {
            mBinding.deviceInfoIdTv.setEnabled(true);
            mBinding.deviceInfoIdTv.setText(deviceInformation.getDeviceID());
        } else {
            mBinding.deviceInfoIdTv.setEnabled(false);
        }

        if (!TextUtils.isEmpty(deviceInformation.getDeviceVersionNumber())) {
            mBinding.deviceInfoVersionTv.setEnabled(true);
            mBinding.deviceInfoVersionTv.setText(deviceInformation.getDeviceVersionNumber());
        } else {
            mBinding.deviceInfoVersionTv.setEnabled(false);
        }

        if (!TextUtils.isEmpty(deviceInformation.getDeviceRemoteAddress())) {
            mBinding.deviceInfoRemoteAddressTv.setEnabled(true);
            mBinding.deviceInfoRemoteAddressTv.setText(deviceInformation.getDeviceRemoteAddress());
        } else {
            mBinding.deviceInfoRemoteAddressTv.setEnabled(false);
        }

        if (!TextUtils.isEmpty(deviceInformation.getHardwareVersionNumber())) {
            mBinding.deviceInfoHardwareTv.setEnabled(true);
            mBinding.deviceInfoHardwareTv.setText(deviceInformation.getHardwareVersionNumber());
        } else {
            mBinding.deviceInfoHardwareTv.setEnabled(false);
        }

        if (!TextUtils.isEmpty(deviceInformation.getSoftwareVersionNumber())) {
            mBinding.deviceInfoSoftwareTv.setEnabled(true);
            mBinding.deviceInfoSoftwareTv.setText(deviceInformation.getSoftwareVersionNumber());
        } else {
            mBinding.deviceInfoSoftwareTv.setEnabled(false);
        }

        if (!TextUtils.isEmpty(deviceInformation.getFirmwareVersionNumber())) {
            mBinding.deviceInfoFirmwareTv.setEnabled(true);
            mBinding.deviceInfoFirmwareTv.setText(deviceInformation.getFirmwareVersionNumber());
        } else {
            mBinding.deviceInfoFirmwareTv.setEnabled(false);
        }

    }

}
