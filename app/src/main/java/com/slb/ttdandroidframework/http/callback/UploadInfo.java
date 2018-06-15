package com.slb.ttdandroidframework.http.callback;

/**
 * 描述：上传信息
 * Created by Lee
 * on 2016/11/3.
 */
public class UploadInfo {


    /**
     * queryData : null
     * created : null
     * oosId : null
     * oosBucket : totodi-test
     * objectKey : totodi-test1a807af3-e169-4734-a0c2-cd38a4a189dd
     * fileName : 【填写规范】中信信托(1).doc
     * url : http://139.224.138.105:5881/totodi-test/totodi-test9a119e52-0d47-44d1-9b75-c20b27920ee5%E3%80%90%E5%A1%AB%E5%86%99%E8%A7%84%E8%8C%83%E3%80%91%E4%B8%AD%E4%BF%A1%E4%BF%A1%E6%89%98%281%29.doc?Expires=1512075864&OSSAccessKeyId=LTAIrPcIm2GdY9sP&Signature=cW5t%2FzEKSsvIQ5o%2BvkIY8lHAu3E%3D
     */

    private Object queryData;
    private Object created;
    private Object oosId;
    private String oosBucket;
    private String objectKey;
    private String fileName;
    private String url;

    public Object getQueryData() {
        return queryData;
    }

    public void setQueryData(Object queryData) {
        this.queryData = queryData;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Object getOosId() {
        return oosId;
    }

    public void setOosId(Object oosId) {
        this.oosId = oosId;
    }

    public String getOosBucket() {
        return oosBucket;
    }

    public void setOosBucket(String oosBucket) {
        this.oosBucket = oosBucket;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "queryData=" + queryData +
                ", created=" + created +
                ", oosId=" + oosId +
                ", oosBucket='" + oosBucket + '\'' +
                ", objectKey='" + objectKey + '\'' +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
