package com.slb.ttdandroidframework.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.frame.utils.ActivityUtil;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ChoiseComEvent;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.ui.activity.ChoiseDataActivity;
import com.slb.ttdandroidframework.ui.adapter.DataAdapter;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.AbstractGatewayService;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.slb.ttdandroidframework.util.io.ObdGatewayService;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DataFragment
        extends BaseMvpFragment<DataContract.IView, DataContract.IPresenter>
        implements DataContract.IView {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mTvAgain)
    ImageView mTvAgain;
    private List<DataEntity> mList = new ArrayList<>();
    private DataAdapter mAdapter;
    private Handler handler = new Handler();
    private
    AbstractGatewayService service;
    private ArrayList<ObdCommand> mCmds = new ArrayList<>();
    private boolean isServiceBound;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            Log.d(TAG, className.toString() + " service is bound");
            isServiceBound = true;
            service = ((AbstractGatewayService.AbstractGatewayServiceBinder) binder).getService();
            service.setContext(_mActivity);
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
            isServiceBound = false;
            Log.d(TAG, className.toString() + " service is unbound");
        }

    };
    private final Runnable mQueueCommands = new Runnable() {
        @Override
        public void run() {
            if (service != null ) {
                queueCommands();
                if(BluetoothUtil.isRunning){
                    handler.postDelayed(mQueueCommands, 2000);
                }
            }
        }
    };

    private void queueCommands() {
        Logger.d("queueCommands:"+ObdConfig.getCommands().size());
        for (ObdCommand Command : mCmds) {
            service.queueJob(new ObdCommandJob(Command));
        }
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static DataFragment newInstance() {
        DataFragment instance = new DataFragment();
        return instance;
    }

    @Override
    public DataContract.IPresenter initPresenter() {
        return new DataPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
//        //测试
//        for (int i = 0; i < 5; i++) {
//            DataEntity entity = new DataEntity();
//            mList.add(entity);
//        }
        mAdapter = new DataAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(_mActivity)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_1)
                        .build());
        initComs();
        Intent serviceIntent = new Intent(_mActivity, ObdGatewayService.class);
        _mActivity.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected boolean rxBusRegist() {
        return true;
    }

    @Subscribe
    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
        if (event.isConnect()) {
            handler.post(mQueueCommands);
        } else {
            handler.removeCallbacks(mQueueCommands);
        }
    }
//    @Subscribe

    public void onObdCommandJobEvent(ObdCommandJob job) {
        boolean isNew = false;
        if (job == null ) {
            return;
        }
        for (DataEntity entity : mAdapter.getData()) {
            if (entity.getTitle().equals(job.getCommand().getName())) {
                entity.setValue(getRealCinnabdResult(job));
                entity.setCheck(true);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        DataEntity dataEntity1 = new DataEntity(job.getCommand().getName(), job.getCommand().getFormattedResult());
        mAdapter.addData(dataEntity1);
        if (mAdapter.getData().size() == 0) {
            DataEntity dataEntity = new DataEntity(job.getCommand().getName(), getRealCinnabdResult(job));
            mAdapter.addData(dataEntity);
        }
    }

    @OnClick(R.id.mTvAgain)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        ArrayList<String> commandName = new ArrayList<>();
        for(ObdCommand command:  mCmds){
            commandName.add(command.getName());
        }
        bundle.putStringArrayList(BizcContant.PARA_CHOISE_DATA,commandName);
        ActivityUtil.next(_mActivity, ChoiseDataActivity.class,bundle,false);
    }
    private void initComs(){
        mCmds.add(new AirIntakeTemperatureCommand());
        mCmds.add(new AmbientAirTemperatureCommand());
        mCmds.add(new EngineCoolantTemperatureCommand());
    }


    @Subscribe
    public void onChociseComEvent(ChoiseComEvent event) {
        if(event.isAdd()){
            mCmds.add(ObdConfig.getCommandForNameIndex(event.getCommandName()));
        }else{
            ObdCommand obdCommand = ObdConfig.getCommandForNameIndex(event.getCommandName());
            int index = 0;
            for(ObdCommand command: mCmds){
                if(command.getName().equals(obdCommand.getName())){
                    index = mCmds.indexOf(command);
                }
            }
            mCmds.remove(index);
            mAdapter.getData().remove(index);
            Logger.d(mCmds.size());
        }
        handler.post(mQueueCommands);
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
