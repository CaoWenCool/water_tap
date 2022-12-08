package com.blockchain.watertap.controller;

import com.blockchain.watertap.service.BaseService;
import com.blockchain.watertap.util.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@Api(tags = "访问界面")
@RequestMapping("/airdrop")
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/index")
    @ApiOperation(
            value = "首页",
            notes = "首页"
    )
    public ModelAndView index(HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String clientType = userAgent.getOperatingSystem().getDeviceType().toString();
        logger.info("device-type" + clientType);
        if(clientType.equals("MOBILE")){
            return new ModelAndView("waterPhone");
        }
        return new ModelAndView("water");
    }
}
