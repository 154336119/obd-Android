package com.slb.ttdandroidframework.http.dns;

/**
 * 描述：
 * Created by Lee
 * on 2017/3/1.
 */

public class DebugDns implements Dns {
    private static DebugDns debugDns;
    public static DebugDns getInstance(){
        if(debugDns == null){
            debugDns = new DebugDns();
        }
        return debugDns;
    }
    @Override
    public String getCommonBaseUrl() {
        return "http://13.59.50.210:9999/";
    }

    @Override
    public String getMsgUrl() {
        return "http://192.168.88.201:8011/";
    }

    @Override
    public String getUploadUrl() {
        return "http://192.168.0.252:8011/zuul/file-service/oos/uploadFile";
    }

    @Override
    public String getUploadForPdfUrl() {
        return "http://192.168.0.252:8011/zuul/file-service/oos/transformToImg?";
    }

    @Override
    public String getWebPanelUrl() {
        return "http://125.69.150.167:8100/signature";
    }

//    @Override
//    public String getContractBaseUrl() {
//        return "http://192.168.0.251:9801/";
////        return "http://api.live.totodi.com/zuul/ess/";
//    }
    @Override
    public String getRiskAnswerUrl() {
        return "http://192.168.0.252:88/evaluation/one";
    }

    @Override
    public String getBasicText() {
        return "https://api.totodi.com/zuul/fs/";
    }

//    @Override
//    public String getOrderUrl() {
//        return "http://192.168.0.252:7008/ttd-fund-order";
//    }



    @Override
    public String getDistributedNimUrl() {
        return "http://106.14.185.151:8048/";
    }

    @Override
    public String getPersonalInvestorBaseInfoRul() {
        return "http://192.168.0.252:88/investor/personal/one";
    }
    @Override
    public String getOrgInvestorBaseInfoRul() {
        return "http://192.168.0.252:88/investor/agency/one";
    }

    @Override
    public String getProductBaseInfoRul() {
        return "http://192.168.0.252:88/investor/product/one";
    }

    @Override
    public String getSysNoticHemlUrl() {
        return "http://192.168.0.252:88/agreeupdate/index";
    }
}
