package com.slb.frame.utils;

import android.content.Context;
import android.util.TypedValue;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class ImagePickerUtils {
    public static ImagePicker headSetting(Context context){
        //跳到图片选择页面
        ImagePicker imagePicker=ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoadUtil());//图片加载器
        imagePicker.setMultiMode(false);//单选还是多选
        imagePicker.setSelectLimit(1);//一次可选择图片张数
        imagePicker.setShowCamera(true);//是否支持拍照


        imagePicker.setCrop(true);//是否支持裁剪
        imagePicker.setSaveRectangle(true);//是否保存裁剪的矩形框

        //裁剪的宽和高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        Integer width = Integer.valueOf("280");
        Integer height = Integer.valueOf("280");
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);

        //保存的宽和高
        imagePicker.setOutPutX(Integer.valueOf("400"));
        imagePicker.setOutPutY(Integer.valueOf("400"));
        return imagePicker;
    }
    public static ImagePicker cardSetting(Context context){
        //跳到图片选择页面
        ImagePicker imagePicker=ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoadUtil());//图片加载器
        imagePicker.setMultiMode(false);//单选还是多选
        imagePicker.setSelectLimit(1);//一次可选择图片张数
        imagePicker.setShowCamera(true);//是否支持拍照


        imagePicker.setCrop(false);//是否支持裁剪
        imagePicker.setSaveRectangle(true);//是否保存裁剪的矩形框

        //裁剪的宽和高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        Integer width = Integer.valueOf("320");
        Integer height = Integer.valueOf("180");
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);

        //保存的宽和高
        imagePicker.setOutPutX(Integer.valueOf("1280"));
        imagePicker.setOutPutY(Integer.valueOf("720"));
        return imagePicker;
    }

    public static ArrayList<ImageItem> getImageItemForStr(List<String> strList){
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        for(String str : strList){
            ImageItem imageItem = new ImageItem();
            imageItem.path = str;
            imageItems.add(imageItem);
        }
        return imageItems;
    }
    public static ArrayList<String> getStrListForImageItem(ArrayList<ImageItem> ImageItemList){
        ArrayList<String> strList = new ArrayList<>();
        for(ImageItem imageItem : ImageItemList){
            strList.add(imageItem.path);
        }
        return strList;
    }
}
