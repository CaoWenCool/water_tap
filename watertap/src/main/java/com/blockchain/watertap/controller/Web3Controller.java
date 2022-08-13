package com.blockchain.watertap.controller;

import com.blockchain.watertap.service.Web3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping(value = "/web3", produces = "application/json")
@Api(tags = "Web3测试")
@RestController
public class Web3Controller {

    private static final Integer SEND_VALUE = 5;

    @Autowired
    Web3Service web3Service;

    @ApiOperation(
            value = "测试",
            notes = "测试"
    )
    @GetMapping(value = "/test")
    public Object getBalance(
            @ApiParam("a")
            @RequestParam(required = false) Integer a,
            @ApiParam("b")
            @RequestParam(required = false) Integer b) {

        return web3Service.getTest(a, b);
    }

    @ApiOperation(
            value = "转账",
            notes = "转账"
    )
    @GetMapping(value = "/transfer")
    public Object transfer(
            @ApiParam("转账地址")
            @RequestParam String toAddress) {

        return web3Service.web3Transfer(toAddress,SEND_VALUE);
    }
}
