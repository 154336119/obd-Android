package com.slb.ttdandroidframework.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.pires.obd.commands.ObdCommand;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ChoiseComEvent;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.ui.adapter.DataAdapter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ChoiseObdGatewayService;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NewChoiseDataActivity
        extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mTvAgain)
    ImageView mTvAgain;
    @BindView(R.id.mTvName)
    TextView mTvName;
    private List<DataEntity> mList = new ArrayList<>();
    private DataAdapter mAdapter;
    private Handler handler = new Handler();
    private AbstractGatewayService service;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, className.toString() + " service is bound");
//            isServiceBound = true;
            service = ((AbstractGatewayService.AbstractGatewayServiceBinder) binder).getService();
            service.setContext(NewChoiseDataActivity.this);
            Log.d(TAG, "Starting live data");
            if (service != null) {
                Logger.d("service1:");
            }
            handler.post(mQueueCommands);
//            try {
//                service.startService();
//            } catch (IOException ioe) {
//            }
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        // This method is *only* called when the connection to the service is lost unexpectedly
        // and *not* when the client unbinds (http://developer.android.com/guide/components/bound-services.html)
        // So the isServiceBound attribute should also be set to false when we unbind from the service.
        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, className.toString() + " service is unbound");
        }

    };
    private final Runnable mQueueCommands = new Runnable() {
        @Override
        public void run() {
            if (service != null) {
                Logger.d("service2:");
            }
            if (service != null) {
                queueCommands();
                handler.postDelayed(mQueueCommands, 2000);
            }
        }
    };

    private void queueCommands() {
        for (ObdCommand Command : ObdConfig.getChoiseCommands()) {
            service.queueJob(new ObdCommandJob(Command));
        }
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static NewChoiseDataActivity newInstance() {
        NewChoiseDataActivity instance = new NewChoiseDataActivity();
        return instance;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_data;
    }



    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if (event.isConnect()) {
            Intent serviceIntent = new Intent(this, ObdGatewayService.class);
            this.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
        } else {
            handler.removeCallbacks(mQueueCommands);
        }
    }

    public void onObdCommandJobEvent(ObdCommandJob job) {
        boolean isNew = false;
        if (job == null) {
            return;
        }
        for (DataEntity entity : mAdapter.getData()) {
            if (entity.getTitle().equals(job.getCommand().getName())) {
                entity.setValue(getRealCinnabdResult(job));
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        DataEntity dataEntity1 = new DataEntity(job.getCommand().getName(),getRealCinnabdResult(job));
        mAdapter.addData(dataEntity1);

        if (mAdapter.getData().size() == 0) {
            DataEntity dataEntity = new DataEntity(job.getCommand().getName(), getRealCinnabdResult(job));
            mAdapter.addData(dataEntity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvAgain.setVisibility(View.GONE);
        mTvName.setText("选择数据");
        mAdapter = new DataAdapter(mList);
        mAdapter.setChoiseData(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_1)
                        .build());
        try {
            if (BluetoothUtil.getSockInstance()!=null) {
                Intent serviceIntent = new Intent(this, ChoiseObdGatewayService.class);
                bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToastMsg("暂无连接设备");
            handler.removeCallbacks(mQueueCommands);
        }

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ObdCommand newCommand = ObdConfig.getChoiseCommands().get(position);
                for (ObdCommand command : ObdConfig.getCommands()){
                    if(command.getName().equals(newCommand.getName())){
                        showToastMsg("该数据已经添加，请重新选择");
                        return;
                    }
                }
                RxBus.get().post(new ChoiseComEvent(ObdConfig.getChoiseCommands().get(position)));
                finish();
            }
        });


    }
    public void stateUpdate(final ObdCommandJob job){
        onObdCommandJobEvent(job);
    }

    public String getRealCinnabdResult(ObdCommandJob job){
        String cmdResult;
        if (job.getState().equals(ObdCommandJob.ObdCommandJobState.EXECUTION_ERROR)) {
            cmdResult = job.getCommand().getResult();
        }  else if (job.getState().equals(ObdCommandJob.ObdCommandJobState.NOT_SUPPORTED)) {
            cmdResult = getString(R.string.status_obd_no_support);
        } else {
            cmdResult = job.getCommand().getFormattedResult();

        }
        return cmdResult;

    }
}
