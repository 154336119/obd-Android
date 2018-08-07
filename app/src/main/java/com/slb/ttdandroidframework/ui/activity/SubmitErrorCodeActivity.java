package com.slb.ttdandroidframework.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.slb.frame.ui.activity.BaseActivity;
import com.slb.frame.utils.DateUtils;
import com.slb.ttdandroidframework.Base;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;
import com.slb.ttdandroidframework.http.bean.VehiclesEntity;
import com.slb.ttdandroidframework.http.callback.ActivityDialogCallback;
import com.slb.ttdandroidframework.http.dns.DnsFactory;
import com.slb.ttdandroidframework.http.model.LzyResponse;
import com.slb.ttdandroidframework.util.config.BizcContant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubmitErrorCodeActivity extends BaseActivity {


    @BindView(R.id.TvSelectCarNo)
    TextView mTvSelectCarNo;
    @BindView(R.id.TvSelectObd)
    TextView mTvSelectObd;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;
    private OptionsPickerView pvObdOptions;
    private OptionsPickerView pvCarOptions;

    private ObdEntity mSelectObdEntity;//选择的OBD
    private VehicleEntity mSelectVehicleEntity;//选择的车辆
    private String confirmPids; //确认故障PID代码集合,多个代码用逗号隔开(A,B,C)
    private String pendingPids; //等待故障PID代码集合,多个代码用逗号隔开(A,B,C)

    @Override
    protected String setToolbarTitle() {
        return "上传故障码";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_error_code;
    }

    @Override
    public void getIntentExtras() {
        super.getIntentExtras();
        confirmPids = getIntent().getStringExtra(BizcContant.PARA_CONFIRM_PIS);
        pendingPids = getIntent().getStringExtra(BizcContant.PARA_PENDING_PIS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.TvSelectCarNo, R.id.TvSelectObd, R.id.btnComfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.TvSelectCarNo:
                if(Base.getUserEntity().getVehicleEntityList()!=null && Base.getUserEntity().getVehicleEntityList().size()>0){
                    initPvCarOptions();
                }
                break;
            case R.id.TvSelectObd:
                if(Base.getUserEntity().getObdEntityList()!=null && Base.getUserEntity().getObdEntityList().size()>0){
                    initPvObdOptions();
                }
                break;
            case R.id.btnComfirm:
                submit();
                break;
        }
    }

    private void initPvObdOptions(){
        pvObdOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                String tx = Base.getUserEntity().getObdEntityList().get(options1).getPickerViewText();
                mTvSelectObd.setText(tx);
                mSelectObdEntity =  Base.getUserEntity().getObdEntityList().get(options1);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择OBD设备")//标题
                .build();

        pvObdOptions.setPicker(Base.getUserEntity().getObdEntityList());//添加数据源
        pvObdOptions.show();
    }

    private void initPvCarOptions(){
        pvCarOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                String tx = Base.getUserEntity().getVehicleEntityList().get(options1).getPickerViewText();
                mTvSelectCarNo.setText(tx);
                mSelectVehicleEntity =  Base.getUserEntity().getVehicleEntityList().get(options1);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择车辆")//标题
                .build();

        pvCarOptions.setPicker(Base.getUserEntity().getVehicleEntityList());//添加数据源
        pvCarOptions.show();
    }

    private void submit(){
        if(mSelectObdEntity == null){
            showToastMsg("请选择OBD");
            return;
        }
        if(mSelectVehicleEntity == null){
            showToastMsg("请选择车辆");
            return;
        }
        OkGo.<LzyResponse<String>>post(DnsFactory.getInstance().getDns().getCommonBaseUrl()+"api/command-log/dtc")
                .tag(this)
                .params("userId",Base.getUserEntity().getId())
                .params("obdId ",mSelectObdEntity.getId())
                .params("vehicleId",mSelectVehicleEntity.getId())
                .params("faultTime", DateUtils.getCurrentTime())
                .params("confirmPids",confirmPids)
                .params("pendingPids",pendingPids)
                .isMultipart(true)
                .headers("Authorization","Bearer "+Base.getUserEntity().getToken())
                .execute(new ActivityDialogCallback<LzyResponse<String>>(this) {
                    @Override
                    public void onSuccess(Response<LzyResponse<String>> response) {
                        finish();
                    }
                });
    }
}
