package com.blockchain.watertap.controller;

import com.blockchain.watertap.model.ApiResult;
import com.blockchain.watertap.model.response.TransferResponse;
import com.blockchain.watertap.service.Web3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping(value = "/web3", produces = "application/json")
@Api(tags = "Web3测试")
@RestController
public class Web3Controller {

    private static final Integer SEND_VALUE = 5;

    @Autowired
    Web3Service web3Service;

    @ApiOperation(
            value = "转账",
            notes = "转账"
    )
    @GetMapping(value = "/transfer")
    public ApiResult transfer(
            @ApiParam("转账地址")
            @RequestParam String toAddress) {
        try {
            web3Service.transferReady(toAddress,SEND_VALUE);
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
        return ApiResult.ok();
    }

    @ApiOperation(
            value = "转账历史记录",
            notes = "转账历史记录"
    )
    @GetMapping(value = "/transfer/history")
    public ApiResult transferHis() {
        try {
            List<TransferResponse> transferResponses =  web3Service.transferHis();
            return ApiResult.ok(transferResponses);
        }catch (Exception e){
            return ApiResult.fail(e.getMessage());
        }
    }

}
