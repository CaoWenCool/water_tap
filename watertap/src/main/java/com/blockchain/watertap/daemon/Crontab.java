package com.blockchain.watertap.daemon;

import com.blockchain.watertap.service.Web3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Crontab {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Web3Service web3Service;

    /**
     * 60秒钟检查一次
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void getEthBalance(){
        web3Service.checkAndSend();
    }

}
