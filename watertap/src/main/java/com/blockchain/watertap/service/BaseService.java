package com.blockchain.watertap.service;

import com.blockchain.watertap.util.IpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BaseService {

    @Value("${water.tab.down.load.url:}")
    private String downloadUrl;

    public static Map<String, LocalDateTime> localDateTimeMap = new HashMap<>();



    public String getDownloadUrL(HttpServletRequest request){
        String ipAddr = IpUtil.getIpAddr(request);
        LocalDateTime expireDateTime =  LocalDateTime.now().plusDays(1);
        localDateTimeMap.put(ipAddr,expireDateTime);
        return downloadUrl;
    }
}
