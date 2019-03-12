package com.slb.ttdandroidframework.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Entity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.slb.ttdandroidframework.http.bean.UserEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.ui.adapter.AddDeviceListdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.ObdHelper;
import com.slb.ttdandroidframework.util.SharedPreferencesUtils;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.weight.CustomDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.util.config.BizcContant.PARA_DEV_ADDR;
import static com.slb.ttdandroidframework.util.config.BizcContant.SP_CONNECT_DEV;
import static com.slb.ttdandroidframework.util.config.BizcContant.SP_IGNORE_OPEN_BLUE;
import static com.slb.ttdandroidframework.util.config.BizcContant.SP_IS_FIRST;

public class AddDeviceListActivity extends BaseActivity {
    private AlertDialog.Builder builder;
    private static final int NO_BLUETOOTH_DEVICE_SELECTED = 0;
    private static final int CANNOT_CONNECT_TO_DEVICE = 1;
    private static final int NO_DATA = 3;
    private static final int OBD_COMMAND_FAILURE = 10;
    private static final int OBD_COMMAND_FAILURE_IO = 11;
    private static final int OBD_COMMAND_FAILURE_UTC = 12;
    private static final int OBD_COMMAND_FAILURE_IE = 13;
    private static final int OBD_COMMAND_FAILURE_MIS = 14;
    private static final int OBD_COMMAND_FAILURE_NODATA = 15;
    private Button btConfirm;
    private Button btCancel ;
    private CheckBox checkBox;
    private boolean isCheck = false ;

    private CustomDialog mCommonAlertDialog;
    private int mTemPosition = 0;
    private Dialog mDialog;
    private Handler mHandler = new Handler(new Handler.Callback() {
        private ObdHelper obdHelper;

        public boolean handleMessage(Message msg) {
            Log.d(TAG, "Message received on handler");
            switch (msg.what) {
                case NO_BLUETOOTH_DEVICE_SELECTED:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    // showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    break;
                case CANNOT_CONNECT_TO_DEVICE:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    //    showToastMsg(getString(R.string.text_bluetooth_error_connecting));
                    break;
                case OBD_COMMAND_FAILURE:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    //   showToastMsg(getString(R.string.text_obd_command_failure));
                    break;
                case OBD_COMMAND_FAILURE_IO:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IO");
                    break;
                case OBD_COMMAND_FAILURE_IE:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " IE");
                    break;
                case OBD_COMMAND_FAILURE_MIS:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
                    //   showToastMsg(getString(R.string.text_obd_command_failure) + " MIS");
                    break;
                case OBD_COMMAND_FAILURE_UTC:
                    showToastMsg(BizcContant.STR_CONNECT_FAILED);
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
                    mTemPosition = position;
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
                        List<ObdEntity> obdEntityList = entity.getObds();
//                        if(entity!=null && entity.getObds()!=null && entity.getObds().size()>0){
                        ///连接状态
                        if (BluetoothUtil.isRunning) {
                            String dev = (String) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.SP_CONNECT_DEV, "");
                            if(!TextUtils.isEmpty(dev)){
                                for(int i=0;i<obdEntityList.size();i++){
                                    ObdEntity obdEntity = obdEntityList.get(i);
                                    if(obdEntity.getBluetoothName().equals(dev)){
                                        obdEntity.setContect(true);
                                        obdEntityList.set(i,obdEntity);
                                    }
                                }
                            }
                        }
                        List<ObdEntity> obdEntities = entity.getObds();
                        UserEntity userEntity = Base.getUserEntity();
                        userEntity.setObdEntityList(obdEntities);
                        Base.setUserEntity(userEntity);
                            mAdapter.setNewData( obdEntities);
                            //提示
                        Boolean isIgnore = (Boolean) SharedPreferencesUtils.getParam(Base.getContext(), BizcContant.SP_IGNORE_OPEN_BLUE, false);
                        if(obdEntities==null || obdEntities.size()==0){
                            if(!isIgnore){
                                showWarning_4();
                            }
                        }
                    }
                });
    }

    @Subscribe
    public void onConnectEvent(ConnectEvent event) {
        showWaitDialog("");
//                dialog.dismiss();
        try {
            if (BluetoothUtil.getSockInstance() != null) {
                RxBus.get().post(new ObdServiceStateEvent(true));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMsg(BizcContant.STR_CONNECT_FAILED);
        }
        hideWaitDialog();
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if (event.isConnect()) {
            BluetoothUtil.setIsRunning(true);
            showToastMsg(BizcContant.STR_CONNECTING_SUCCESS);
            ObdEntity obdEntity =  Base.getUserEntity().getObdEntityList().get(mTemPosition);
            SharedPreferencesUtils.setParam(AddDeviceListActivity.this, SP_CONNECT_DEV,obdEntity.getBluetoothName());
            Base.getUserEntity().getObdEntityList().set(mTemPosition,obdEntity);
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
            showToastMsg(BizcContant.STR_CONNECT_FAILED);
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
//                        ObdSEntity entity = response.body().data;
//                        Base.getUserEntity().setObdEntityList(mAdapter.getData());
                    }
                });
    }
    /**
     * 断开连接dialog
     */
    private void showDisConnectDialog() {
        CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog
                .setTitle(getString(R.string.prompt))
                .setMessage(getString(R.string.prompt_disconnect_content))
                .setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothUtil.setIsRunning(false);
                        BluetoothUtil.setSockInstance(null);
                        RxBus.get().post(new ObdServiceStateEvent(false));
                        RxBus.get().post(new ObdConnectStateEvent(false));
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton(getString(R.string.NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mCommonAlertDialog = dialog.create();
        mCommonAlertDialog.setCanceledOnTouchOutside(false);
        mCommonAlertDialog.show();
    }

//    /**
//     * dialog
//     */
//    private void showWarning_4() {
//        builder = new AlertDialog.Builder(this)
//                .setMessage(R.string.Warning_4).setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        SharedPreferencesUtils.setParam(AddDeviceListActivity.this, SP_IS_FIRST,false);
//                        dialogInterface.dismiss();
//                    }
//                }).setNegativeButton(getString(R.string.Ignore_this_message), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        SharedPreferencesUtils.setParam(AddDeviceListActivity.this, SP_IGNORE_OPEN_BLUE,true);
//                        dialogInterface.dismiss();
//                    }
//                });
//        builder.create().show();
//    }

    private void showWarning_4(){
        mDialog = new Dialog(this);
        //去除标题栏
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View           dialogView     = inflater.inflate(R.layout.dialog_normal_layout_one, null);
        //将自定义布局设置进去
        mDialog.setContentView(dialogView);
        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = mDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        mDialog.show();
        window.setAttributes(lp);

        //设置点击其它地方不让消失弹窗
        mDialog.setCancelable(false);
        initDialogView(dialogView);

        initDialogListener();
    }

    private void initDialogView(View view) {
        btConfirm = (Button) view.findViewById(R.id.positiveButton);
        btCancel = (Button) view.findViewById(R.id.negativeButton);
        checkBox = (CheckBox) view.findViewById(R.id.cb);
    }

    private void initDialogListener() {
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setParam(AddDeviceListActivity.this, SP_IS_FIRST,false);
                mDialog.dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                if(isCheck){
                    SharedPreferencesUtils.setParam(AddDeviceListActivity.this, SP_IGNORE_OPEN_BLUE,true);
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
            }
        });
    }

}
