package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBaseFragmentPresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;

/**
 * Created by Gifford on 2018/1/5.
 */

public class HistoryContract {
	public interface IView extends IBaseLoadingDialogView {
	}
	public interface IPresenter<T> extends IBaseFragmentPresenter<T> {
		void getHistoryErrorCode(ObdEntity obd , VehicleEntity vehicle, String startDate, String endDate);
	}
}
