package com.hndt.controller;

import com.hndt.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * URL 管理
 *
 * @author Hystar
 * @date 2018/11/20 0020
 */
@RequestMapping(value = "/url")
@Controller
public class UrlController {

    @Resource
    private UrlService urlService;

    /**
     * URL加密，返回：url?token=xxx
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/encode", method = RequestMethod.POST)
    @ResponseBody
    public String UrlEncode(@RequestParam(value = "url") String url) {
        return urlService.operatorUrl(url);
    }

    /**
     * URL 验证
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public void validate(@RequestParam(value = "token", defaultValue = "pass") String token, HttpServletResponse response) {
        int status = urlService.validate(token);
        if (status == 200) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
    }
}
