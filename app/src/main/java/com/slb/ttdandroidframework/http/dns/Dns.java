package com.slb.ttdandroidframework.http.dns;

/**
 * 描述：
 * Created by Lee
 * on 2017/3/1.
 */

public interface Dns {
    /**通用接口**/
    public String getCommonBaseUrl();
    /**消息接口**/
    public String getMsgUrl();
    /**上传接口**/
    public String getUploadUrl();
    /**pdf显示接口**/
    public String getUploadForPdfUrl();
    /**手写板**/
    public String getWebPanelUrl();
    /**个人-风险调查问卷 **/
    public String getRiskAnswerUrl();

    /**获取纯文本*/
    public String getBasicText();

    public String getDistributedNimUrl();

    /**投资者信息（个人）*/
    public String getPersonalInvestorBaseInfoRul();
    /**投资者信息（个人）*/
    public String getOrgInvestorBaseInfoRul();
    /**投资者信息（产品买产品）*/
    public String getProductBaseInfoRul();
    /**系统公告*/
    public String getSysNoticHemlUrl();
}
