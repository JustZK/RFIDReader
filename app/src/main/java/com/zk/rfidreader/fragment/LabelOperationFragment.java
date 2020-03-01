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

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.activity.HomeActivity;
import com.zk.rfidreader.databinding.FragmentLabelOperationBinding;

import java.lang.ref.WeakReference;

public class LabelOperationFragment extends Fragment implements View.OnClickListener{
    private FragmentLabelOperationBinding mBinding;

    private LabelOperationFragmentHandler mHandler;
    private void handleMessage(Message msg) {
        switch (msg.what) {

        }
    }

    public static LabelOperationFragment newInstance() {
        LabelOperationFragment fragment = new LabelOperationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
                R.layout.fragment_label_operation, container, false);
        mBinding.setOnClickListener(this);
        mHandler = new LabelOperationFragmentHandler(this);



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
        switch (v.getId()){

        }
    }

    private static class LabelOperationFragmentHandler extends Handler {
        private final WeakReference<LabelOperationFragment> labelOperationFragmentWeakReference;

        LabelOperationFragmentHandler(LabelOperationFragment labelOperationFragment) {
            super();
            labelOperationFragmentWeakReference = new WeakReference<>(labelOperationFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (labelOperationFragmentWeakReference.get() != null) {
                labelOperationFragmentWeakReference.get().handleMessage(msg);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            LogUtil.Companion.getInstance().d("isVisibleToUser");
            if (mBinding != null) {
                mBinding.labelOperationIdTv.setText("设备编号：" + ((HomeActivity) getActivity()).getDeviceID());
            }
        } else {
            LogUtil.Companion.getInstance().d("!!!isVisibleToUser");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

}
