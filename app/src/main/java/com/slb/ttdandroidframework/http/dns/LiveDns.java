package com.slb.ttdandroidframework.http.dns;

/**
 * 描述：
 * Created by Lee
 * on 2017/3/1.
 */

public class LiveDns implements Dns {
    private static LiveDns liveDns;
    public static LiveDns getInstance(){
        if(liveDns == null){
            liveDns = new LiveDns();
        }
        return liveDns;
    }
    @Override
    public String getCommonBaseUrl() {
        return "http://erp.veepeak.net:9999/";
    }

    @Override
    public String getMsgUrl() {
        return "http://125.69.150.167:17811/";
    }

    @Override
    public String getUploadUrl() {
        return "http://125.69.150.167:17811/zuul/file-service/oos/uploadFile";
    }

    @Override
    public String getUploadForPdfUrl() {
        return "http://125.69.150.167:17811/zuul/file-service/oos/transformToImg?";
    }

    @Override
    public String getWebPanelUrl() {
        return "http://125.69.150.167:8100/signature";
    }


    @Override
    public String getRiskAnswerUrl() {
        return "http://125.69.150.167:17088/evaluation/one";
    }

    @Override
    public String getBasicText() {
        return "https://api.totodi.com/zuul/fs/";
    }


    @Override
    public String getDistributedNimUrl() {
        return "http://106.14.185.151:8048/";

    }

    @Override
    public String getPersonalInvestorBaseInfoRul() {
        return "http://125.69.150.167:17088/investor/personal/one";
    }
    @Override
    public String getOrgInvestorBaseInfoRul() {
        return "http://125.69.150.167:17088/investor/agency/one";
    }

    @Override
    public String getProductBaseInfoRul() {
        return "http://125.69.150.167:17088/investor/product/one";
    }

    @Override
    public String getSysNoticHemlUrl() {
        return "http://125.69.150.167:17088/agreeupdate/index";
    }
}
