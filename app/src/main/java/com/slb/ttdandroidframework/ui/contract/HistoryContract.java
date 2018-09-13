package com.slb.ttdandroidframework.ui.contract;

import com.slb.frame.ui.presenter.IBaseFragmentPresenter;
import com.slb.frame.ui.view.IBaseLoadingDialogView;
import com.slb.ttdandroidframework.http.bean.HistoryErrorCodeEntity;
import com.slb.ttdandroidframework.http.bean.ObdEntity;
import com.slb.ttdandroidframework.http.bean.VehicleEntity;

import java.util.List;

/**
 * Created by Gifford on 2018/1/5.
 */

public class HistoryContract {
	public interface IView extends IBaseLoadingDialogView {
		void getHistoryErrorCodeSuccess(List<HistoryErrorCodeEntity> list);
	}
	public interface IPresenter<T> extends IBaseFragmentPresenter<T> {
		void getHistoryErrorCode(ObdEntity obd , VehicleEntity vehicle, String startDate, String endDate);
	}
}
