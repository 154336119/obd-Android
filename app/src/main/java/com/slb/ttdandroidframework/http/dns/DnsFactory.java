package com.slb.ttdandroidframework.http.dns;


/**
 * Created by Administrator on 2017/11/1.
 */

public class DnsFactory {
    private Dns mDns;
    private static DnsFactory mDnsFactory;
    public static DnsFactory getInstance(){
        if(mDnsFactory == null){
            mDnsFactory = new DnsFactory();
        }
        return mDnsFactory;
    }
    public Dns getDns(){
        if(mDns == null){
            mDns  = new DebugDns();
        }
        return mDns;
    }
    public void clearDns(){
        mDns = null;
    }
}
