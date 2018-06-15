package com.slb.ttdandroidframework.http.dns;

/**
 * 描述：
 * Created by Lee
 * on 2017/3/1.
 */

public class ReleaseDns implements Dns {
    private static ReleaseDns releaseDns;
    public static ReleaseDns getInstance(){
        if(releaseDns == null){
            releaseDns = new ReleaseDns();
        }
        return releaseDns;
    }

    @Override
    public String getCommonBaseUrl() {
        return "http://139.224.138.105:13811/";
    }

    @Override
    public String getMsgUrl() {
        return "http://139.224.138.105:13811/";
    }

    @Override
    public String getUploadUrl() {
        return "http://139.224.138.105:13811/zuul/file-service/oos/uploadFile";
    }

    @Override
    public String getUploadForPdfUrl() {
        return "http://139.224.138.105:13811/zuul/file-service/oos/transformToImg?";
    }
    @Override
    public String getWebPanelUrl() {
        return "http://125.69.150.167:8100/signature";
    }
//    @Override
//    public String getContractBaseUrl() {
//        return "http://192.168.0.251:9801/";
//    }

    @Override
    public String getRiskAnswerUrl() {
        return "http://139.224.138.105:8500/evaluation/one";
    }

    @Override
    public String getBasicText() {
        return "https://api.totodi.com/zuul/fs/";
    }

//    @Override
//    public String getOrderUrl() {
//        return "http://139.224.138.105:13811/ttd-fund-order";
//    }

    @Override
    public String getDistributedNimUrl() {
        return "http://106.14.185.151:8048/";
    }

    @Override
    public String getPersonalInvestorBaseInfoRul() {
        return "http://139.224.138.105:8500/investor/personal/one";
    }
    @Override
    public String getOrgInvestorBaseInfoRul() {
        return "http://139.224.138.105:8500/investor/agency/one";
    }

    @Override
    public String getProductBaseInfoRul() {
        return "http://139.224.138.105:8500/investor/product/one";
    }

    @Override
    public String getSysNoticHemlUrl() {
        return "http://139.224.138.105:8500/agreeupdate/index";
    }
}
