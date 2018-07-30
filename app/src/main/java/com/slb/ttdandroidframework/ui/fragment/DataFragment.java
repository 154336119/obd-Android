package com.slb.ttdandroidframework.ui.fragment;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.logger.Logger;
import com.slb.frame.ui.fragment.BaseMvpFragment;
import com.slb.ttdandroidframework.MyApplication;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.ObdConnectStateEvent;
import com.slb.ttdandroidframework.http.bean.DataEntity;
import com.slb.ttdandroidframework.http.bean.HistoryDriveDataEntity;
import com.slb.ttdandroidframework.ui.adapter.DataAdapter;
import com.slb.ttdandroidframework.ui.adapter.HistoryDriveDataAdapter;
import com.slb.ttdandroidframework.ui.contract.DataContract;
import com.slb.ttdandroidframework.ui.presenter.DataPresenter;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DataFragment
        extends BaseMvpFragment<DataContract.IView, DataContract.IPresenter>
        implements DataContract.IView {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private List<DataEntity> mList = new ArrayList<>();
    private DataAdapter mAdapter;
    private Handler handler = new Handler();
    private final Runnable mQueueCommands = new Runnable() {
        @Override
        public void run() {
            if (MyApplication.getService() != null && MyApplication.getService().isRunning() && MyApplication.getService().queueEmpty()) {
                queueCommands();
            }
            handler.postDelayed(mQueueCommands,2000);
        }
    };

    private void queueCommands() {
        for (ObdCommand Command : ObdConfig.getCommands()) {
            MyApplication.getService().queueJob(new ObdCommandJob(Command));
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
        if(event.isConnect()){
            handler.post(mQueueCommands);
        }else{
            handler.removeCallbacks(mQueueCommands);
        }
    }
    @Subscribe
    public void onObdCommandJobEvent(ObdCommandJob job) {
        if(job == null ||job.getState() == ObdCommandJob.ObdCommandJobState.EXECUTION_ERROR){
            return;
        }
        for(DataEntity entity : mAdapter.getData()){
            if(entity.getTitle().equals(job.getCommand().getName())){
                entity.setValue(job.getCommand().getFormattedResult());
                mAdapter.notifyDataSetChanged();
            }else{
                DataEntity dataEntity = new DataEntity(job.getCommand().getName(),job.getCommand().getFormattedResult());
                mAdapter.addData(dataEntity);
            }
        }
        if(mAdapter.getData().size()==0){
            DataEntity dataEntity = new DataEntity(job.getCommand().getName(),job.getCommand().getFormattedResult());
            mAdapter.addData(dataEntity);
        }
    }

}
