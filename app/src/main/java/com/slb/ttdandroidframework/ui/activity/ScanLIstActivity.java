package com.slb.ttdandroidframework.ui.activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwangjr.rxbus.RxBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.event.RefreshObdListtEvent;
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.callback.DialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.http.service.ComServiceUrl;
import com.slb.ttdandroidframework.ui.adapter.AddDeviceListdapter;
import com.slb.ttdandroidframework.ui.adapter.ScanListdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.weight.CustomDialog;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.MDEditDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slb.ttdandroidframework.util.config.BizcContant.PARA_DEV_ADDR;

public class ScanLIstActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private CustomDialog mCommonAlertDialog;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
    private ScanListdapter mAdapter;

    @Override
    protected String setToolbarTitle() {
        return "Scan List";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_list;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        getBondedDevices();
        mAdapter = new ScanListdapter(deviceList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                createDialog(mAdapter.getItem(position));
            }
        });
    }

    public void getBondedDevices() {
        deviceList.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceList.add(device);
                Log.d(TAG, "已绑定设备:   " + device.getName() + " - " + device.getAddress());
            }
        }
        if (deviceList.size() > 0) {
            SharedPreferencesUtils.setParam(ScanLIstActivity.this, PARA_DEV_ADDR, deviceList.get(0).getAddress());
        }
        initRv();
    }

    private void initRv(){
        mAdapter = new ScanListdapter(deviceList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void createDialog(final BluetoothDevice device){
        new MDEditDialog.Builder(ScanLIstActivity.this).setTitleVisible(true)
                .setTitleText("产品型号")
                .setTitleTextSize(20)
                .setMaxLength(50)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(18)
                .setHintText("请输入产品型号")
                .setMaxLines(1)
                .setContentTextColor(R.color.colorPrimary)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.colorPrimary)
                .setLeftButtonText("取消")
                .setRightButtonTextColor(R.color.colorPrimary)
                .setRightButtonText("确定")
                .setLineColor(R.color.colorPrimary)
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>
                        () {
                    @Override
                    public void clickLeftButton(MDEditDialog dialog, View view) {
                        dialog.getEditTextContent();
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(MDEditDialog dialog, View view) {
                        if(TextUtils.isDigitsOnly(dialog.getEditTextContent())){
                            showToastMsg("请输入内容");
                            return;
                        }
                        addObd(device.getName(),device.getAddress(),dialog.getEditTextContent());
                        dialog.dismiss();
                    }
                })
                .setMinHeight(0.3f)
                .setWidth(0.8f)
                .build()
                .show();
    }


    public void addObd(String bluetoothName, String bluetoothAddress ,String obdModel) {
        String id = Base.getUserEntity().getId();
        OkGo.<LzyResponse<UserEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl() + ComServiceUrl.addObd)//
                .tag(this)//
                .params("bluetoothName", bluetoothName)
                .params("bluetoothAddress", bluetoothAddress)
                .params("obdModel", obdModel)
                .params("userId", id)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .headers("Authorization", "Bearer " + Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<UserEntity>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<UserEntity>> response) {
                        com.hwangjr.rxbus.RxBus.get().post(new RefreshObdListtEvent());
                        finish();
                    }
                });
    }
}
