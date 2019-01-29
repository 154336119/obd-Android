package com.slb.ttdandroidframework.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigkoo.pickerview.model.IPickerViewData;


public class ObdEntity implements IPickerViewData, Parcelable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String obdModel;
    private String bluetoothName;
    private String bluetoothAddress;

    public String getObdModel() {
        return obdModel;
    }

    public void setObdModel(String obdModel) {
        this.obdModel = obdModel;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public ObdEntity() {
    }

    @Override
    public String getPickerViewText() {
        return bluetoothName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.obdModel);
        dest.writeString(this.bluetoothName);
        dest.writeString(this.bluetoothAddress);
    }

    protected ObdEntity(Parcel in) {
        this.id = in.readString();
        this.obdModel = in.readString();
        this.bluetoothName = in.readString();
        this.bluetoothAddress = in.readString();
    }

    public static final Parcelable.Creator<ObdEntity> CREATOR = new Parcelable.Creator<ObdEntity>() {
        @Override
        public ObdEntity createFromParcel(Parcel source) {
            return new ObdEntity(source);
        }

        @Override
        public ObdEntity[] newArray(int size) {
            return new ObdEntity[size];
        }
    };
}
