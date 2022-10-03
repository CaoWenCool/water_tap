package com.blockchain.watertap.controller;

import com.blockchain.watertap.model.ApiResult;
import com.blockchain.watertap.model.response.InitResponse;
import com.blockchain.watertap.model.response.TransferResponse;
import com.blockchain.watertap.service.BaseService;
import com.blockchain.watertap.service.Web3Service;
import com.blockchain.watertap.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequestMapping(value = "/web3", produces = "application/json")
@Api(tags = "Web3测试")
@RestController
public class Web3Controller {

    private static final Integer SEND_VALUE = 19;

    @Autowired
    Web3Service web3Service;

    @Autowired
    BaseService baseService;

    @ApiOperation(
            value = "转账",
            notes = "转账"
    )
    @GetMapping(value = "/transfer")
    public ApiResult transfer(
            @ApiParam("转账地址")
            @RequestParam String toAddress) {
        try {
            web3Service.transferReady(toAddress, SEND_VALUE);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.ok();
    }

    @ApiOperation(
            value = "转账历史记录",
            notes = "转账历史记录"
    )
    @GetMapping(value = "/transfer/history")
    public ApiResult transferHis(HttpServletRequest request) {
        InitResponse initResponse = new InitResponse();
        try {
            List<TransferResponse> transferResponses = web3Service.transferHis();
            String ipAddr = IpUtil.getIpAddr(request);
            LocalDateTime localDateTime = BaseService.localDateTimeMap.get(ipAddr);
            if (null != localDateTime) {
                LocalDateTime now = LocalDateTime.now();
                if (now.isBefore(localDateTime)) {
                    initResponse.setClickDownload(true);
                }
            }
            initResponse.setTransferResponses(transferResponses);
            return ApiResult.ok(initResponse);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }


    @ApiOperation(
            value = "转账",
            notes = "转账"
    )
    @GetMapping(value = "/download/url")
    public ApiResult getDownloadUrl(HttpServletRequest request) {
        String url = baseService.getDownloadUrL(request);
        return ApiResult.ok(url);
    }


}
