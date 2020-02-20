package com.zk.rfidreader.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zk.rfid.ur880.UR880Entrance;
import com.zk.rfidreader.R;
import com.zk.rfidreader.adapter.DeviceAdapter;
import com.zk.rfidreader.databinding.FragmentLabelInventoryBinding;

public class LabelInventoryFragment extends Fragment implements View.OnClickListener{
    private FragmentLabelInventoryBinding mBinding;

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
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_label_inventory, container, false);
        mBinding.setOnClickListener(this);
        mBinding.labelInventoryInventoryModeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mBinding.labelInventoryStopBtn.setVisibility(View.GONE);
                } else {
                    mBinding.labelInventoryStopBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            case R.id.label_inventory_start_btn:

                break;
            case R.id.label_inventory_stop_btn:

                break;
            case R.id.label_inventory_clear_btn:

                break;
        }
    }
}
