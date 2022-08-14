package com.blockchain.watertap.controller;

import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(tags = "访问界面")
@RequestMapping("/water/tab")
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
        if(clientType.equals("MOBILE")){
            logger.info("device-type" + clientType);
            return new ModelAndView("waterPhone");
        }
        return new ModelAndView("water");
    }
}
