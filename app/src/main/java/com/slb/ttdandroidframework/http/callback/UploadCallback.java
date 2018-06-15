//package com.slb.ttdandroidframework.http.callback;
//
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 描述：
// * Created by Lee
// * on 2016/12/28.
// */
//public abstract  class UploadCallback extends AbsCallback<List<UploadInfo>> {
//    @Override
//    public List<UploadInfo> convertSuccess(Response response) throws Exception {
//        List<UploadInfo> list = new ArrayList<UploadInfo>();
//        String s = StringConvert.create().convertSuccess(response);
//        JSONObject jb = JSONObject.parseObject(s);
//        int code = jb.getInteger("code");
//        if(code != 0 ){
//            onError(null ,null ,new ApiException(String.valueOf(code)));
//        }
//        JSONObject  jbData = jb.getJSONObject("data");
//        JSONArray jsonArray = jbData.getJSONArray("list");
//        for(int i=0 ;i<jsonArray.size();i++){
//            UploadInfo info = new UploadInfo();
//            info.setFileName(jsonArray.getJSONObject(i).getString("fileName"));
//            info.setObjectKey(jsonArray.getJSONObject(i).getString("objectKey"));
//            info.setOosBucket(jsonArray.getJSONObject(i).getString("oosBucket"));
//            info.setUrl(jsonArray.getJSONObject(i).getString("url"));
//            list.add(info);
//        }
//        response.close();
//        return list;
//    }
//
//}
