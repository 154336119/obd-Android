package com.slb.ttdandroidframework.ui.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.hwangjr.rxbus.RxBus;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.event.RefreshVehicleListtEvent;
import com.slb.ttdandroidframework.http.bean.CarMakeEntity;
import com.slb.ttdandroidframework.http.bean.CarModelEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;
import com.slb.ttdandroidframework.ui.contract.CarInfoContract;
import com.slb.ttdandroidframework.ui.presenter.CarInfoPresenter;
import com.slb.ttdandroidframework.util.config.BizcContant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加 查看车辆
 */
public class CarInfoActivity extends BaseMvpActivity<CarInfoContract.IView, CarInfoContract.IPresenter>
        implements CarInfoContract.IView {
    @BindView(R.id.edtMake)
    TextView edtMake;
    @BindView(R.id.edtModel)
    TextView edtModel;
    @BindView(R.id.btnComfirm)
    Button btnComfirm;
    @BindView(R.id.edtYear)
    TextView edtYear;
    @BindView(R.id.edtName)
    EditText edtName;
    //操作
    private int mOperation = BizcContant.ADD;
    private VehicleEntity mVehicleEntity;
    private MenuItem mMenuItem;

    private OptionsPickerView pvCarBrandOptions;
    private OptionsPickerView pvCarModelOptions;
    private List<CarMakeEntity> mCarBrandList = new ArrayList<>();
    private List<CarModelEntity> mCarModelList = new ArrayList<>();
    private CarMakeEntity mSeleceCarBrandEntity;
    private CarModelEntity mSelectCarModelEntity;

    @Override
    protected String setToolbarTitle() {
        if (mOperation == BizcContant.ADD) {
            return "添加车辆";
        } else if (mOperation == BizcContant.EDIT) {
            return "修改车辆";
        }
        return null;
    }

    @Override
    public void getIntentExtras() {
        super.getIntentExtras();
        mOperation = getIntent().getIntExtra(BizcContant.PARA_OPERATION, BizcContant.ADD);
        mVehicleEntity = getIntent().getParcelableExtra(BizcContant.PARA_CAR);
    }

    @Override
    public CarInfoContract.IPresenter initPresenter() {
        return new CarInfoPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (mOperation == BizcContant.EDIT) {
            mSeleceCarBrandEntity = mVehicleEntity.getModel().getMake();
            mSelectCarModelEntity = mVehicleEntity.getModel();
            edtName.setText(mVehicleEntity.getName());
            edtYear.setText(mVehicleEntity.getYear());
            edtMake.setText(mSeleceCarBrandEntity.getName());
            edtModel.setText(mSelectCarModelEntity.getName());
            mPresenter.getCarModeList(mSeleceCarBrandEntity.getId());
            if (mMenuItem != null) {
                mMenuItem.setVisible(true);
            }
        }
        mPresenter.getCarBrandList();
        //initCarBrandOptionPicker();
    }


    @Override
    public void addCarSuccess() {
        RxBus.get().post(new RefreshVehicleListtEvent());
//        RxBus.getInstance().post(new RefreshVehicleListtEvent());
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.edtMake, R.id.edtModel, R.id.btnComfirm, R.id.edtYear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edtYear:
                hideSoftInput();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);//当前年
                int month = calendar.get(Calendar.MONTH);//当前月
                int day = calendar.get(Calendar.DAY_OF_MONTH);//当前日
                //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
                DatePickerDialog dialog = new DatePickerDialog(CarInfoActivity.this, DatePickerDialog.THEME_HOLO_LIGHT, mStartDateListener, year, month, day);
                dialog.show();
                break;
            case R.id.edtMake:
                hideSoftInput();
                if (mCarBrandList != null) {
                    pvCarBrandOptions.show();
                }
                break;
            case R.id.edtModel:
                hideSoftInput();
                if (mCarModelList != null && pvCarModelOptions != null) {
                    pvCarModelOptions.show();
                }
                break;
            case R.id.btnComfirm:
                if (mOperation == BizcContant.ADD) {
                    mPresenter.addCar(
                            mSelectCarModelEntity
                            , edtYear.getText().toString()
                            , edtName.getText().toString());
                } else if (mOperation == BizcContant.EDIT) {
                    mPresenter.editCar(mVehicleEntity.getId()
                            , mSelectCarModelEntity
                            , edtYear.getText().toString()
                            , edtName.getText().toString());
                }
                break;
        }
    }

    @Override
    public void edidCarSuccess() {
        RxBus.get().post(new RefreshVehicleListtEvent());
        finish();
    }

    @Override
    public void delectCarSuccess() {
        RxBus.get().post(new RefreshVehicleListtEvent());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        mMenuItem = menu.getItem(0);
        if (mOperation == BizcContant.ADD) {
            mMenuItem.setVisible(false);
        } else if (mOperation == BizcContant.EDIT) {
            mMenuItem.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.delectCar(mVehicleEntity.getId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCarBrandListSuccess(List<CarMakeEntity> list) {
        mCarBrandList = list;
        initCarBrandOptionPicker();
    }

    @Override
    public void getCarModeListSuccess(List<CarModelEntity> list) {
        mCarModelList = list;
        initCarModeOptionPicker();
    }

    private void initCarBrandOptionPicker() {//条件选择器初始化
        pvCarBrandOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                edtMake.setText(mCarBrandList.get(options1).getName());
                mSeleceCarBrandEntity = mCarBrandList.get(options1);
                mSelectCarModelEntity = null;
                mPresenter.getCarModeList(mSeleceCarBrandEntity.getId());
            }
        })
                .setTitleText("类型选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvCarBrandOptions.setPicker(mCarBrandList);//二级选择器
    }

    private void initCarModeOptionPicker() {//条件选择器初始化
        pvCarModelOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                edtModel.setText(mCarModelList.get(options1).getName());
                mSelectCarModelEntity = mCarModelList.get(options1);
            }
        })
                .setTitleText("类型选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvCarModelOptions.setPicker(mCarModelList);//二级选择器
    }

    private DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;
            String mresult = String.format("%0" + 2 + "d", monthOfYear);
            edtYear.setText(years + "-" + mresult + "-" + String.format("%0" + 2 + "d", dayOfMonth));
        }
    };

}
