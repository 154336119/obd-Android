package com.slb.ttdandroidframework.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pires.obd.commands.ObdCommand;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.ui.adapter.ChoiseDataAdapter;
import com.slb.ttdandroidframework.util.config.BizcContant;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.slb.ttdandroidframework.util.io.ObdCommandJob;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ChoiseDataActivity
        extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mTvAgain)
    ImageView mTvAgain;
    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    private ChoiseDataAdapter mAdapter;
    private ArrayList<String> mChoiseCmdsName = new ArrayList<>();

    @Override
    public void getIntentExtras() {
        mChoiseCmdsName = getIntent().getStringArrayListExtra(BizcContant.PARA_CHOISE_DATA);
    }


    @Override
    protected boolean hasToolbar() {
        return false;
    }

    public static ChoiseDataActivity newInstance() {
        ChoiseDataActivity instance = new ChoiseDataActivity();
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

    //    @Subscribe
//    public void onObdConnectStateEvent(ObdConnectStateEvent event) {
//        if (event.isConnect()) {
//            Intent serviceIntent = new Intent(this, ObdGatewayService.class);
//            this.bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
//        } else {
//            handler.removeCallbacks(mQueueCommands);
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTvAgain.setVisibility(View.GONE);
        mTvName.setText("选择数据");
        mAdapter = new ChoiseDataAdapter(ObdConfig.getAllCommandsName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_1)
                        .build());


//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ObdCommand newCommand = ObdConfig.getChoiseCommands().get(position);
//                for (ObdCommand command : ObdConfig.getCommands()){
//                    if(command.getName().equals(newCommand.getName())){
//                        showToastMsg("该数据已经添加，请重新选择");
//                        return;
//                    }
//                }
//                RxBus.get().post(new ChoiseComEvent(ObdConfig.getChoiseCommands().get(position)));
//                finish();
//            }
//        });

        mAdapter.setmSparseArray(initSparseArray());
    }

    public void stateUpdate(final ObdCommandJob job) {
    }

    private SparseArray<String> initSparseArray() {
        SparseArray<String> sparseArrayNames = new SparseArray<String>();
        if (mChoiseCmdsName != null && mChoiseCmdsName.size() > 0) {
            for (int i = 0; i < mChoiseCmdsName.size(); i++) {
                for (int j = 0; j < ObdConfig.getAllCommandsName().size(); j++) {
                    if (ObdConfig.getAllCommandsName().get(j).equals(mChoiseCmdsName.get(i))) {
                        sparseArrayNames.put(j, mChoiseCmdsName.get(i));
                    }
                }
            }
        }
        return sparseArrayNames;
    }

    @OnClick(R.id.mIvBack)
    public void onViewClicked() {
        finish();
    }
}
