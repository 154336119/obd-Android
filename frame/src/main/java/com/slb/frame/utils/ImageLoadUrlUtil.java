package com.slb.frame.utils;

import android.text.TextUtils;

/**
 * 描述：服务器动态裁剪
 * Created by Lee
 * on 2016/9/19.
 */
public class ImageLoadUrlUtil {
    public final static String BIG_IMAGE_URL = "?imageView2/1/w/400/format/yjpg/q/70";
    public final static String SMALL_IMAGE_URL = "?imageView2/1/w/200/format/yjpg/q/70";
    public final static String IMAGE_URL_400_400  = "?imageView2/1/w/400/format/yjpg/q/70";
    public final static String IMAGE_URL_200_200 = "?imageView2/1/w/200/format/yjpg/q/70";
    public final static String IMAGE_URL_640_340 = "?imageView2/1/w/640/h/340/format/yjpg/q/70";
    public final static String IMAGE_URL_750_375 = "?imageView2/1/w/750/h/375/format/yjpg/q/70";
    public final static String IMAGE_URL_750= "?imageView2/2/w/750/format/yjpg/q/70";

    /**
     * 图片大小转换类
     * @param url
     * @param type 需要转换的类型（200×200 、400×400、640×340）
     * @return
     */
    public static String converImageSize(String url ,String type){
        if(TextUtils.isEmpty(url)){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append(type);
        return sb.toString();
    }
}
