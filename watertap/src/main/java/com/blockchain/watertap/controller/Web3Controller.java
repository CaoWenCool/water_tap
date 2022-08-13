package com.blockchain.watertap.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping(value = "/web3", produces = "application/json")
@Api(tags = "货币的价格")
@RestController
public class Web3Controller {


    @ApiOperation(
            value = "获取账户余额",
            notes = "获取账户余额"
    )
    @GetMapping(value = "/account/balance")
    public String getBalance(){



        return null;
    }
}
