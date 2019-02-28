package com.slb.ttdandroidframework.ui.activity;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pires.obd.commands.control.DtcNumberCommand;
import com.github.pires.obd.commands.control.PendingTroubleCodesCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.hwangjr.rxbus.RxBus;
import com.slb.frame.ui.activity.BaseMvpActivity;
import com.slb.ttdandroidframework.R;
import com.slb.ttdandroidframework.command.mil.Command0141;
import com.slb.ttdandroidframework.command.mil.EmissionObdMultiCommand;
import com.slb.ttdandroidframework.event.ObdServiceStateEvent;
import com.slb.ttdandroidframework.http.bean.EmissionTestEntity;
import com.slb.ttdandroidframework.ui.adapter.EmissionTestAdapter;
import com.slb.ttdandroidframework.ui.contract.EmissionTestContract;
import com.slb.ttdandroidframework.ui.presenter.EmissionTestPresenter;
import com.slb.ttdandroidframework.util.BluetoothUtil;
import com.slb.ttdandroidframework.util.config.ObdConfig;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.slb.ttdandroidframework.ui.activity.ReadErrorCodeActivity.DATA_OK_CODE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IE;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_IO;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_MIS;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_NODATA;
import static com.slb.ttdandroidframework.util.config.ObdConfig.OBD_COMMAND_FAILURE_UTC;

public class EmissionTestActivity extends BaseMvpActivity<EmissionTestContract.IView, EmissionTestContract.IPresenter>
        implements EmissionTestContract.IView {

    @BindView(R.id.mTvName)
    TextView mTvName;
    @BindView(R.id.mIvBack)
    ImageView mIvBack;
    @BindView(R.id.mTvAgain)
    TextView mTvAgain;
    @BindView(R.id.can_content_view)
    RecyclerView canContentView;
    private BluetoothSocket sock;
    private EmissionTestTask emissionTestTask;
    private EmissionTestAdapter mAdapter;

    private List<EmissionTestEntity> mList = new ArrayList<>();
    private Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "Message received on handler");
            switch (msg.what) {
                case ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED:
                    showToastMsg(getString(R.string.text_bluetooth_nodevice));
                    break;
                case ObdConfig.CANNOT_CONNECT_TO_DEVICE:
                    showToastMsg(getString(R.string.text_bluetooth_error_connecting));
                    break;
                case ObdConfig.OBD_COMMAND_FAILURE:
                    showToastMsg(getString(R.string.text_obd_command_failure));
                    break;
                case OBD_COMMAND_FAILURE_IO:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " IO");
                    break;
                case OBD_COMMAND_FAILURE_IE:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " IE");
                    break;
                case OBD_COMMAND_FAILURE_MIS:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " MIS");
                    break;
                case OBD_COMMAND_FAILURE_UTC:
                    showToastMsg(getString(R.string.text_obd_command_failure) + " UTC");
                    break;
                case OBD_COMMAND_FAILURE_NODATA:
                    showToastMsg(getString(R.string.text_noerrors));
                    break;
                case ObdConfig.NO_DATA:
                    showToastMsg(getString(R.string.text_dtc_no_data));
                    ///finish();
                    break;
                case DATA_OK_CODE:
//                    dataOk((String) msg.obj);
                    mList = (List<EmissionTestEntity>)msg.obj;
                    mAdapter.setNewData(mList);
                    break;
            }
            return false;
        }
    });
    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public EmissionTestContract.IPresenter initPresenter() {
        return new EmissionTestPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_emission_test;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
//
//        //测试
//        for (int i = 0; i < 2; i++) {
//            EmissionTestEntity emissionTestEntity = new EmissionTestEntity();
//            mList.add(emissionTestEntity);
//        }

        mAdapter = new EmissionTestAdapter(mList);
        canContentView.setLayoutManager(new LinearLayoutManager(this));
        canContentView.setAdapter(mAdapter);
        canContentView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#2B3139"))
                        .sizeResId(R.dimen.distance_10)
                        .build());

        try {
            sock = BluetoothUtil.getSockInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executeMode6Command();
    }
    /**
     * 请求mode6
     */
    public void executeMode6Command() {
        try {
            if (BluetoothUtil.getDeviceInstance() == null) {
                mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            } else {
                emissionTestTask = new EmissionTestTask();
                emissionTestTask.execute();
            }
        } catch (IOException e) {
            mHandler.obtainMessage(ObdConfig.NO_BLUETOOTH_DEVICE_SELECTED).sendToTarget();
            e.printStackTrace();
        }
    }
    //    @Override
//    protected Observable<HttpResult<Object, CookBillEntity>> requestHttp() {
//        return RetrofitSerciveFactory.provideComService().getCookBillList(1);
//    }
    private class EmissionTestTask extends AsyncTask<String, Integer, List<EmissionTestEntity>> {
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            showWaitDialog("loading...");
        }

        @Override
        protected List<EmissionTestEntity> doInBackground(String... params) {
            int troubleCodesNum = 0;
            int pendingTroubleCodesNum = 0;
            List<EmissionTestEntity> list = new ArrayList<>();
            //Get the current thread's token
            synchronized (this) {
                TroubleCodesCommand troubleCodesCommand = new TroubleCodesCommand();
                try {
                    troubleCodesCommand.run(sock.getInputStream(), sock.getOutputStream());
                    if(!TextUtils.isEmpty(troubleCodesCommand.getFormattedResult())){
                        troubleCodesNum = troubleCodesCommand.getFormattedResult().split("\n").length;
                    }
                } catch (IOException e) {
//                    showConnectFailDialog();
                    e.printStackTrace();
                } catch (InterruptedException e) {
//                    showConnectFailDialog();
                    e.printStackTrace();
                } catch (NoDataException e) {
                    Log.e("DTCERR", e.getMessage());
                }catch (UnableToConnectException e) {
//                    showConnectFailDialog();
                    Log.e("DTCERR", e.getMessage());
                }


                PendingTroubleCodesCommand pendingTroubleCodesCommand = new PendingTroubleCodesCommand();
                try {
                    pendingTroubleCodesCommand.run(sock.getInputStream(), sock.getOutputStream());
                    if(!TextUtils.isEmpty(pendingTroubleCodesCommand.getFormattedResult())) {
                        pendingTroubleCodesNum = pendingTroubleCodesCommand.getFormattedResult().split("\n").length;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (NoDataException e) {
                }
                try {
                    EmissionObdMultiCommand emissionObdMultiCommand = new EmissionObdMultiCommand();
                    emissionObdMultiCommand.add(new Command0141("01 41"));
                    emissionObdMultiCommand.add(new DtcNumberCommand());
                    emissionObdMultiCommand.sendCommands(sock.getInputStream(), sock.getOutputStream());
                    list = emissionObdMultiCommand.getList(troubleCodesNum,pendingTroubleCodesNum);
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_IO).sendToTarget();
                    return null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_IE).sendToTarget();
                    return null;
                } catch (UnableToConnectException e) {
                    e.printStackTrace();
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_UTC).sendToTarget();
                    return null;
                } catch (MisunderstoodCommandException e) {
                    e.printStackTrace();
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_MIS).sendToTarget();
                    return null;
                } catch (NoDataException e) {
                    Log.e("DTCERR", e.getMessage());
//                    mHandler.obtainMessage(OBD_COMMAND_FAILURE_NODATA).sendToTarget();
//                    mHandler.obtainMessage(DATA_OK_CODE, list).sendToTarget();
                } catch (Exception e) {
                    Log.e("DTCERR", e.getMessage());
                    mHandler.obtainMessage(ObdConfig.OBD_COMMAND_FAILURE).sendToTarget();
                } finally {
                    hideWaitDialog();
                }

            }

            return list;
        }
        public void closeSocket(BluetoothSocket sock) {
            if (sock != null)
                // close socket
                try {
                    sock.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<EmissionTestEntity> result) {
            hideWaitDialog();
            mHandler.obtainMessage(DATA_OK_CODE, result).sendToTarget();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.mIvBack, R.id.mTvAgain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mIvBack:
                finish();
                break;
            case R.id.mTvAgain:
                mTvAgain.setText(getString(R.string.rescan));
                executeMode6Command();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().post(new ObdServiceStateEvent(true));
    }

}
