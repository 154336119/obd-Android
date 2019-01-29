package com.slb.ttdandroidframework.ui.activity;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ConnectEvent;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.event.RefreshMineObdListtEvent;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.ObdSEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.ui.adapter.AddDeviceListdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.ObdHelper;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.weight.CustomDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.util.config.BizcContant.PARA_DEV_ADDR;

public class AddDeviceListActivity extends BaseActivity {
    private static final int NO_BLUETOOTH_DEVICE_SELECTED = 0;
    private static final int CANNOT_CONNECT_TO_DEVICE = 1;
    private static final int NO_DATA = 3;
    private static final int OBD_COMMAND_FAILURE = 10;
    private static final int OBD_COMMAND_FAILURE_IO = 11;
    private static final int OBD_COMMAND_FAILURE_UTC = 12;
    private static final int OBD_COMMAND_FAILURE_IE = 13;
    private static final int OBD_COMMAND_FAILURE_MIS = 14;
    private static final int OBD_COMMAND_FAILURE_NODATA = 15;
    private CustomDialog mCommonAlertDialog;
    private Handler mHandler = new Handler(new Handler.Callback() {
        private ObdHelper obdHelper;

        public boolean handleMessage(Message msg) {
            Log.d(TAG, "Message received on handler");
            switch (msg.what) {
                case NO_BLUETOOTH_DEVICE_SELECTED:
                    showToastMsg("连接失败");
                    // showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    break;
                case CANNOT_CONNECT_TO_DEVICE:
                    showToastMsg("连接失败");
                    //    showToastMsg(getString(R.string.text_bluetooth_error_connecting));
                    break;
                case OBD_COMMAND_FAILURE:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure));
                    break;
                case OBD_COMMAND_FAILURE_IO:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IO");
                    break;
                case OBD_COMMAND_FAILURE_IE:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IE");
                    break;
                case OBD_COMMAND_FAILURE_MIS:
                    showToastMsg("连接失败");
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " MIS");
                    break;
                case OBD_COMMAND_FAILURE_UTC:
                    showToastMsg("连接失败");
                    //  showToastMsg(getString(R.string.text_obd_command_failure) + " UTC");
                    break;
                case OBD_COMMAND_FAILURE_NODATA:
                    RxBus.get().post(new ObdServiceStateEvent(true));
                    // showToastMsg(getString(R.string.text_noerrors));
                    break;
                case NO_DATA:
                    showToastMsg(getString(R.string.text_dtc_no_data));
                    break;
            }
            hideWaitDialog();
            return false;
        }
    });
    @BindView(R.id.TvAdd)
    TextView mTvAdd;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    AddDeviceListdapter mAdapter;
    ArrayList<ObdEntity> mlist = new ArrayList<>();
    ObdEntity mTemObdEntity;
    @Override
    protected String setToolbarTitle() {
        return getString(R.string.device);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_device_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new AddDeviceListdapter(Base.getUserEntity().getObdEntityList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        getObdList();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (BluetoothUtil.isRunning) {
                    showDisConnectDialog();
                }else{
                    String addr = mAdapter.getItem(position).getBluetoothAddress();
                    SharedPreferencesUtils.setParam(AddDeviceListActivity.this, PARA_DEV_ADDR,addr);
                    BluetoothUtil.setRemoteDevice(addr);
                    ObdHelper obdHelper = new ObdHelper(mHandler, AddDeviceListActivity.this);
                    obdHelper.connectToDevice();
                }
            }
        });

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // 开启滑动删除
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                 mTemObdEntity = mAdapter.getItem(pos);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                delObd(mTemObdEntity.getId());
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
    }

    @OnClick(R.id.TvAdd)
    public void onViewClicked() {
        ActivityUtil.next(this,ScanLIstActivity.class);
    }


    /**
     * 获取obd列表
     */
    public void getObdList(){
        OkGo.<LzyResponse<ObdSEntity>>get(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/user/"+Base.getUserEntity().getId()+"/obd")
                .tag(this)//
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<ObdSEntity>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ObdSEntity>> response) {
                        ObdSEntity entity = response.body().data;
//                        if(entity!=null && entity.getObds()!=null && entity.getObds().size()>0){
                            Base.getUserEntity().setObdEntityList(entity.getObds());
                            mAdapter.setNewData( Base.getUserEntity().getObdEntityList());
//                        }
                    }
                });
    }

    @Subscribe
    public void onConnectEvent(ConnectEvent event) {
        showWaitDialog("连接中");
//                dialog.dismiss();
        try {
            if (BluetoothUtil.getSockInstance() != null) {
                RxBus.get().post(new ObdServiceStateEvent(true));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMsg("连接失败");
        }
        hideWaitDialog();
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if (event.isConnect()) {
            BluetoothUtil.setIsRunning(true);
            showToastMsg("连接成功");
            finish();
//            MyApplication.getService().queueJob(new ObdCommandJob(new PendingTroubleCodesCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EchoOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new LineFeedOffCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new TimeoutCommand(62)));
//            MyApplication.getService().queueJob(new ObdCommandJob(new AmbientAirTemperatureCommand()));
//            MyApplication.getService().queueJob(new ObdCommandJob(new EngineCoolantTemperatureCommand()));
            hideWaitDialog();
        } else {
            BluetoothUtil.setIsRunning(false);
            showToastMsg("连接失败");
        }
    }

    @Override
    protected boolean rxBusRegist() {
        return true;
    }


    /**
     * 获取obd列表
     */
    public void delObd(String userObdId){
        OkGo.<LzyResponse<ObdSEntity>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/user/remove-obd")
                .tag(this)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .params("userObdId",userObdId)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<ObdSEntity>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<ObdSEntity>> response) {
                        ObdSEntity entity = response.body().data;
                        Base.getUserEntity().setObdEntityList(mAdapter.getData());
                    }
                });
    }
    /**
     * 断开连接dialog
     */
    private void showDisConnectDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog
                .setTitle("提示")
                .setMessage("是否断开设备连接？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothUtil.setIsRunning(false);
                        BluetoothUtil.setSockInstance(null);
                        RxBus.get().post(new ObdServiceStateEvent(false));
                        RxBus.get().post(new ObdConnectStateEvent(false));
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mCommonAlertDialog = dialog.create();
        mCommonAlertDialog.setCanceledOnTouchOutside(false);
        mCommonAlertDialog.show();
    }

}
