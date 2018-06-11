package danfei.shulaibao.widget.wheel;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import danfei.shulaibao.widget.R;
import danfei.shulaibao.widget.wheel.adapter.CityWheelAdapter;
import danfei.shulaibao.widget.wheel.adapter.DistrictWheelAdapter;
import danfei.shulaibao.widget.wheel.adapter.ProWheelAdapter;
import danfei.shulaibao.widget.wheel.bean.City;
import danfei.shulaibao.widget.wheel.bean.District;
import danfei.shulaibao.widget.wheel.bean.Province;


public class DistrictWheelMain {

	private Context mContext;

	private View view;
	private WheelVerticalView wvPro;
	private WheelVerticalView wvCity;
	 // Scrolling flag
    private boolean scrolling = false;
    private List<Province> mDistrictAreaList;//省、城市、区信息

	private WheelVerticalView wvDistrict;

	private TextView mTvTtile;
	private Button mBtnCancel;
	private Button mBtnEnsure;

	private Dialog mSelectDialog;
	//只用于createHouseActivity中
	private DistrictWheelAdapter<Camera.Area> disAdapter;
	//只用于createHouseActivity中
	private CityWheelAdapter<City> cityAdapter;

	public void setmNoDistrict(boolean mNoDistrict) {
		this.mNoDistrict = mNoDistrict;
	}

	//没有区
	private boolean mNoDistrict = false;
	public DistrictWheelMain(Context context,List<Province> list) {
		this.mContext = context;
		this.mDistrictAreaList = list;
		setView(view);
	}


	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void init() {
		view = LayoutInflater.from(mContext).inflate(R.layout.view_city_wheel,null);
		wvPro = (WheelVerticalView) view.findViewById(R.id.one);
		//省
		ProWheelAdapter<Province> proAdapter = new ProWheelAdapter<Province>(mContext,mDistrictAreaList);
		proAdapter.setItemResource(R.layout.view_city_wheel_item);
		proAdapter.setItemTextResource(R.id.name);
		wvPro.setViewAdapter(proAdapter);// 设置"省"的显示数据
		wvPro.setCyclic(false);// 可循环滚动
		wvPro.setCurrentItem(0);// 初始化时显示的数据
		wvPro.setVisibleItems(5);
		//市
		CityWheelAdapter<City> cityAdapter = new CityWheelAdapter<City>(mContext,mDistrictAreaList.get(0).getChildrenList());
		cityAdapter.setItemResource(R.layout.view_city_wheel_item);
		cityAdapter.setItemTextResource(R.id.name);
		wvCity = (WheelVerticalView) view.findViewById(R.id.two);
		wvCity.setViewAdapter(cityAdapter);
		wvCity.setCyclic(false);
		wvCity.setCurrentItem(0);
		wvCity.setVisibleItems(5);
		//区
		DistrictWheelAdapter<District> disAdapter = new DistrictWheelAdapter<District>(mContext,mDistrictAreaList.get(0).getChildrenList().get(0).getChildrenList());
		cityAdapter.setItemResource(R.layout.view_city_wheel_item);
		cityAdapter.setItemTextResource(R.id.name);
		wvDistrict = (WheelVerticalView) view.findViewById(R.id.three);
		wvDistrict.setViewAdapter(disAdapter);
		wvDistrict.setCyclic(false);
		wvDistrict.setCurrentItem(0);
		wvDistrict.setVisibleItems(5);
		updateDistrict(wvDistrict, 0);

		// 添加"省"监听
		OnWheelChangedListener proWheelListener = new OnWheelChangedListener() {
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				 if (!scrolling) {
					 updateCities(wvCity, newValue);
					 updateDistrict(wvDistrict, 0);
				 }
			}

		};

		OnWheelScrollListener proScrollingListener = new OnWheelScrollListener() {
            public void onScrollingStarted(AbstractWheel wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(AbstractWheel wheel) {
                scrolling = false;
                updateCities(wvCity, wvPro.getCurrentItem());
                updateDistrict(wvDistrict, 0);
            }
        };

		// 添加"市"监听
		OnWheelChangedListener cityWheelListener_month = new OnWheelChangedListener() {
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				if (!scrolling) {
					 updateDistrict(wvDistrict, newValue);
				 }
			}
		};
		OnWheelScrollListener cityScrollingListener = new OnWheelScrollListener() {
            public void onScrollingStarted(AbstractWheel wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(AbstractWheel wheel) {
                scrolling = false;
                updateDistrict(wvDistrict, wvCity.getCurrentItem());
            }
        };
		wvPro.addChangingListener(proWheelListener);
		wvPro.addScrollingListener(proScrollingListener);
		wvCity.addChangingListener(cityWheelListener_month);
		wvCity.addScrollingListener(cityScrollingListener);
		if(mNoDistrict){
			wvDistrict.setVisibility(View.GONE);
		}
	}

	public void initProCityNoScrolling() {
		wvPro = (WheelVerticalView) view.findViewById(R.id.one);
		//省
		ProWheelAdapter<Province> proAdapter = new ProWheelAdapter<Province>(mContext,mDistrictAreaList);
		proAdapter.setItemResource(R.layout.view_city_wheel_item);
		proAdapter.setItemTextResource(R.id.name);
		wvPro.setViewAdapter(proAdapter);// 设置"省"的显示数据
		wvPro.setCyclic(false);// 可循环滚动
		wvPro.setCurrentItem(0);// 初始化时显示的数据
		wvPro.setVisibleItems(5);
		//市
		CityWheelAdapter<City> cityAdapter = new CityWheelAdapter<City>(mContext,mDistrictAreaList.get(0).getChildrenList());
		cityAdapter.setItemResource(R.layout.view_city_wheel_item);
		cityAdapter.setItemTextResource(R.id.name);
		wvCity = (WheelVerticalView) view.findViewById(R.id.two);
		wvCity.setViewAdapter(cityAdapter);
		wvCity.setCyclic(false);
		wvCity.setCurrentItem(0);
		wvCity.setVisibleItems(5);
		//区
		DistrictWheelAdapter<District> disAdapter = new DistrictWheelAdapter<District>(mContext,mDistrictAreaList.get(0).getChildrenList().get(0).getChildrenList());
		cityAdapter.setItemResource(R.layout.view_city_wheel_item);
		cityAdapter.setItemTextResource(R.id.name);
		wvDistrict = (WheelVerticalView) view.findViewById(R.id.three);
		wvDistrict.setViewAdapter(disAdapter);
		wvDistrict.setCyclic(false);
		wvDistrict.setCurrentItem(0);
		wvDistrict.setVisibleItems(5);
		updateDistrict(wvDistrict, 0);
	}

	protected void updateDistrict(AbstractWheel district, int newValue) {
		if(mNoDistrict){
			return;
		}
		List<District> list = mDistrictAreaList.get(wvPro.getCurrentItem()).getChildrenList().get(newValue).getChildrenList();
		if(list==null){
			wvDistrict.setVisibility(View.INVISIBLE);
			return;
		}else{
			wvDistrict.setVisibility(View.VISIBLE);
		}
		DistrictWheelAdapter<District> districtAdapter = new DistrictWheelAdapter<District>(mContext,list);
    	districtAdapter.setItemResource(R.layout.view_city_wheel_item);
    	districtAdapter.setItemTextResource(R.id.name);
    	district.setViewAdapter(districtAdapter);
    	district.setCurrentItem(0);
	}

	/**
     * Updates the city spinnerwheel
     */
    private void updateCities(AbstractWheel city, int index) {
		List<City> list = mDistrictAreaList.get(index).getChildrenList();
		if(list==null){
			wvCity.setVisibility(View.INVISIBLE);
			return;
		}else{
			wvCity.setVisibility(View.VISIBLE);
		}
    	CityWheelAdapter<City> cityAdapter = new CityWheelAdapter<City>(mContext,list);
    	cityAdapter.setItemResource(R.layout.view_city_wheel_item);
    	cityAdapter.setItemTextResource(R.id.name);
        city.setViewAdapter(cityAdapter);
        city.setCurrentItem(0);
    }

    public String getPro() {
    	//return one[wvPro.getCurrentItem()];
    	return mDistrictAreaList.get(wvPro.getCurrentItem()).getAreaName();
    }

	public String getCity() {
		return mDistrictAreaList.get(wvPro.getCurrentItem()).getChildrenList().get(wvCity.getCurrentItem()).getAreaName();
	}
	public String getDistrict() {
		return mDistrictAreaList.get(wvPro.getCurrentItem()).getChildrenList().get(wvCity.getCurrentItem()).getChildrenList().get(wvDistrict.getCurrentItem()).getAreaName();
	}

	public int getProId() {
		//return one[wvPro.getCurrentItem()];
		return mDistrictAreaList.get(wvPro.getCurrentItem()).getAreaId();
	}

	public int getCityId() {
		return mDistrictAreaList.get(wvPro.getCurrentItem()).getChildrenList().get(wvCity.getCurrentItem()).getAreaId();
	}
	public int getDistrictId() {
		return mDistrictAreaList.get(wvPro.getCurrentItem()).getChildrenList().get(wvCity.getCurrentItem()).getChildrenList().get(wvDistrict.getCurrentItem()).getAreaId();
	}


	public void setDistrictGone(){
		wvDistrict.setVisibility(View.GONE);
	}

	public void setProCurPos(int pos){
		wvPro.setCurrentItem(pos);
	}
	public void setCityCurPos(int pos){
		wvCity.setCurrentItem(pos);
	}
//	public void setCraertHouseAllAdapter(ProWheelAdapter<DistrictArea> proAdapter,CityWheelAdapter<AreaItem> cityAdapter,DistrictWheelAdapter<Area> disAdapter){
//		wvPro.setViewAdapter(proAdapter);// 设置"省"的显示数据
//		wvCity.setViewAdapter(cityAdapter);
//		wvDistrict.setViewAdapter(disAdapter);
//		this.disAdapter = disAdapter;
//		this.cityAdapter = cityAdapter;
//	}

//	public String getDistrictName(){
//		return (String) disAdapter.getItemText(wvDistrict.getCurrentItem());
//	}
//
//	public String getCityName(){
//		return (String) cityAdapter.getItemText(wvCity.getCurrentItem());
//	}
//
//	public String getDistrictAreaId(){
//		return (String) disAdapter.getItemAreaId(wvDistrict.getCurrentItem());
//	}


	public void showDialog() {
		if (null == mSelectDialog) {
			mSelectDialog = new Dialog(mContext, R.style.DatetimePickerDialog);
			Window window = mSelectDialog.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			WindowManager.LayoutParams layoutParams = window.getAttributes();
			layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
			layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			layoutParams.gravity = Gravity.BOTTOM;
			mSelectDialog.getWindow().setAttributes(layoutParams);
			mSelectDialog.setContentView(view);
		}
		if (mBtnCancel == null) {
			setCancelClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSelectDialog.dismiss();
				}
			});
		}
		if (mBtnEnsure == null) {
			setEnsureClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSelectDialog.dismiss();
				}
			});
		}
		mSelectDialog.show();
	}

	public void dismissDialog() {
		if (null != mSelectDialog) {
			mSelectDialog.dismiss();
		}
	}

	public void setCancelClickListener(View.OnClickListener listener) {
		mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
		mBtnCancel.setVisibility(View.VISIBLE);
		mBtnCancel.setOnClickListener(listener);
	}

	public void setEnsureClickListener(View.OnClickListener listener) {
		mBtnEnsure = (Button) view.findViewById(R.id.btn_ensure);
		mBtnEnsure.setVisibility(View.VISIBLE);
		mBtnEnsure.setOnClickListener(listener);
	}
}
