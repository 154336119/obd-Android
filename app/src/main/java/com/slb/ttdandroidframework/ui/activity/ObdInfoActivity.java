package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.frame.utils.rx.RxBus;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.RefreshObdListtEvent;
import com.slb.ttdandroidframework.event.RefreshVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.ui.contract.ObdInfoContract;
import com.slb.ttdandroidframework.ui.presenter.ObdInfoPresenter;
import com.slb.ttdandroidframework.util.config.BizcContant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ObdInfoActivity extends BaseMvpActivity<ObdInfoContract.IView, ObdInfoContract.IPresenter>
        implements ObdInfoContract.IView {

    @BindView(R.id.edtSerialNumber)
    EditText edtSerialNumber;
    @BindView(R.id.edtModelNumber)
    EditText edtModelNumber;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;
    //操作
    private int mOperation = BizcContant.ADD;
    private ObdEntity mEditObdEntity;

    private MenuItem mMenuItem;

    @Override
    protected String setToolbarTitle() {
        if(mOperation == BizcContant.ADD){
            return "添加设备";
        }else if(mOperation == BizcContant.EDIT){
            return "修改设备";
        }
        return null;
    }
    @Override
    public void getIntentExtras() {
        super.getIntentExtras();
        mOperation = getIntent().getIntExtra(BizcContant.PARA_OPERATION,BizcContant.ADD);
        mEditObdEntity = getIntent().getParcelableExtra(BizcContant.PARA_ODB);
    }

    @Override
    public ObdInfoContract.IPresenter initPresenter() {
        return new ObdInfoPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bod_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if(mOperation == BizcContant.EDIT){
//            edtSerialNumber.setText(mEditObdEntity.getSerialsNumber());
//            edtModelNumber.setText(mEditObdEntity.getProductModel());
            if(mMenuItem!=null){
                mMenuItem.setVisible(true);
            }
        }
    }

    @OnClick(R.id.btnComfirm)
    public void onViewClicked() {
        if(mOperation == BizcContant.ADD){
            mPresenter.addObd(edtSerialNumber.getText().toString(),edtModelNumber.getText().toString());
        }else if(mOperation == BizcContant.EDIT){
            mPresenter.editObd(mEditObdEntity.getId(),edtSerialNumber.getText().toString(),edtModelNumber.getText().toString());
        }
    }

    @Override
    public void addObdSuccess() {
        com.hwangjr.rxbus.RxBus.get().post(new RefreshObdListtEvent());
        finish();
    }

    @Override
    public void edidObdCarSuccess() {
       com.hwangjr.rxbus.RxBus.get().post(new RefreshObdListtEvent());
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        mMenuItem = menu.getItem(0);
        if(mOperation == BizcContant.ADD){
            mMenuItem.setVisible(false);
        }else if(mOperation == BizcContant.EDIT){
            mMenuItem.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.delectObd(mEditObdEntity.getId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void delectObdSuccess() {
        com.hwangjr.rxbus.RxBus.get().post(new RefreshObdListtEvent());
        finish();
    }
}
