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
import android.widget.AdapterView;
import android.widget.Toast;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.LabelInfo;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.InventoryListener;
import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.activity.HomeActivity;
import com.zk.rfidreader.adapter.DeviceAdapter;
import com.zk.rfidreader.adapter.LabelAdapter;
import com.zk.rfidreader.databinding.FragmentLabelInventoryBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LabelInventoryFragment extends Fragment implements View.OnClickListener {
    private final int START = 0x01;
    private final int END = 0x02;
    private final int CANCEL = 0x03;
    private final int VALUE = 0x04;

    private View mView;
    private FragmentLabelInventoryBinding mBinding;
    private List<LabelInfo> mLabelInfoList = new ArrayList<>();
    private LabelAdapter mLabelAdapter;

    private LabelInventoryFragmentHandler mHandler;

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case START:
                Toast.makeText(getContext(), "开始盘点！", Toast.LENGTH_SHORT).show();
                break;
            case END:
                Toast.makeText(getContext(), "盘点结束！", Toast.LENGTH_SHORT).show();
                break;
            case CANCEL:
                Toast.makeText(getContext(), "停止盘点！", Toast.LENGTH_SHORT).show();
                break;
            case VALUE:
                LabelInfo labelInfo = (LabelInfo) msg.obj;
                boolean isExit = false;
                for (LabelInfo labelInfo1 : mLabelInfoList) {
                    if (labelInfo1.equals(labelInfo)) {
                        isExit = true;
                        labelInfo1.setRSSI(labelInfo.getRSSI());
                        labelInfo1.setFastID(labelInfo.getFastID());
                        labelInfo1.setAntennaNumber(labelInfo.getAntennaNumber());
                        labelInfo1.setDeviceID(labelInfo.getDeviceID());
                        labelInfo1.setOperatingTime(labelInfo.getOperatingTime());
                        labelInfo1.setTID(labelInfo.getTID());
                        labelInfo1.setInventoryNumber(labelInfo1.getInventoryNumber() + 1);
                    }
                }
                if (!isExit) {
                    labelInfo.setInventoryNumber(1);
                    mLabelInfoList.add(labelInfo);
                }
                mLabelAdapter.notifyDataSetChanged();
                mBinding.labelInventoryNumberTv.setText("当前盘到的标签数量：" + mLabelInfoList.size());
                break;
        }
    }

    public static LabelInventoryFragment newInstance() {
        LabelInventoryFragment fragment = new LabelInventoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_label_inventory, container, false);
            mBinding.setOnClickListener(this);
            mBinding.labelInventoryInventoryModeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        mBinding.labelInventoryStopBtn.setVisibility(View.GONE);
                    } else {
                        mBinding.labelInventoryStopBtn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            mLabelAdapter = new LabelAdapter(getContext(), mLabelInfoList);
            mBinding.labelInventoryLv.setAdapter(mLabelAdapter);
            mHandler = new LabelInventoryFragmentHandler(this);
            UR880Entrance.getInstance().addOnInventoryListener(mInventoryListener);

            mView = mBinding.getRoot();
        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (null != parent) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    private InventoryListener mInventoryListener = new InventoryListener() {
        @Override
        public void startInventory(int resultCode) {
            mHandler.sendEmptyMessage(START);
        }

        @Override
        public void endInventory(int resultCode) {
            mHandler.sendEmptyMessage(END);
        }

        @Override
        public void inventoryValue(LabelInfo labelInfo) {
            Message message = Message.obtain();
            message.what = VALUE;
            message.obj = labelInfo;
            mHandler.sendMessage(message);
        }

        @Override
        public void cancel(int result, int code) {
            mHandler.sendEmptyMessage(CANCEL);
        }
    };

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
            case R.id.label_inventory_start_btn:
                mLabelInfoList.clear();
                mBinding.labelInventoryNumberTv.setText("当前盘到的标签数量：" + mLabelInfoList.size());
                String ID = ((HomeActivity) getActivity()).getDeviceID();
                int fastId = mBinding.labelInventoryFastIdSp.getSelectedItemPosition();
                int antennaNumber = mBinding.labelInventoryAntennaNumberSp.getSelectedItemPosition();
                int inventoryType = mBinding.labelInventoryInventoryModeSp.getSelectedItemPosition();
                UR880Entrance.getInstance().send(new UR880SendInfo.Builder().inventory(ID, fastId, antennaNumber, inventoryType).build());
                break;
            case R.id.label_inventory_stop_btn:
                UR880Entrance.getInstance().send(new UR880SendInfo.Builder().cancel(((HomeActivity) getActivity()).getDeviceID()).build());
                break;
            case R.id.label_inventory_clear_btn:
                mLabelInfoList.clear();
                mBinding.labelInventoryNumberTv.setText("当前盘到的标签数量：" + mLabelInfoList.size());
                mLabelAdapter.notifyDataSetChanged();
                break;
        }
    }

    private static class LabelInventoryFragmentHandler extends Handler {
        private final WeakReference<LabelInventoryFragment> labelInventoryFragmentWeakReference;

        LabelInventoryFragmentHandler(LabelInventoryFragment labelInventoryFragment) {
            super();
            labelInventoryFragmentWeakReference = new WeakReference<>(labelInventoryFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (labelInventoryFragmentWeakReference.get() != null) {
                labelInventoryFragmentWeakReference.get().handleMessage(msg);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            LogUtil.Companion.getInstance().d("isVisibleToUser");
            if (mBinding != null && getActivity() != null && ((HomeActivity) getActivity()).getDeviceID() != null) {
                mBinding.labelDeviceIdTv.setText("设备编号：" + ((HomeActivity) getActivity()).getDeviceID());
            }
        } else {
            LogUtil.Companion.getInstance().d("!!!isVisibleToUser");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
