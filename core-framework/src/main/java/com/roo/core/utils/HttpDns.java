package com.roo.core.utils;


import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.http.DnspodFree;
import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class HttpDns implements Dns {

    private DnsManager dnsManager;

    public HttpDns() {
        IResolver[] resolvers;
        try {
            if (DnsManager.needHttpDns()) {
                resolvers = new IResolver[2];
                resolvers[0] = new DnspodFree();
                resolvers[1] = AndroidDnsServer.defaultResolver();
                dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
            } else {
                resolvers = new IResolver[2];
                resolvers[0] = AndroidDnsServer.defaultResolver();
                resolvers[1] = new Resolver(InetAddress.getByName("8.8.8.8"));
                dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (dnsManager == null)  //当构造失败时使用默认解析方式
            return Dns.SYSTEM.lookup(hostname);

        try {
            String[] ips = dnsManager.query(hostname);  //获取HttpDNS解析结果
            if (ips == null || ips.length == 0) {
                return Dns.SYSTEM.lookup(hostname);
            }

            List<InetAddress> result = new ArrayList<>();
            for (String ip : ips) {  //将ip地址数组转换成所需要的对象列表
                result.addAll(Arrays.asList(InetAddress.getAllByName(ip)));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //当有异常发生时，使用默认解析
        return Dns.SYSTEM.lookup(hostname);
    }
}