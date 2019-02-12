package com.hndt.service;

import com.hndt.utils.MD5Util;
import com.hndt.utils.StringUtil;
import com.hndt.utils.TokenCache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hystar
 * @date 2018/11/20 0020
 */
@Service
@Getter
@Slf4j
public class UrlService {

    /**
     * 秘钥
     */
    @Value("${SecretKey}")
    private String key;

    @Value("${passURL}")
    private String passUrl;

    /**
     * 有效期 5min
     */
    private static final int PERIOD_VALIDITY = 5 * 60 * 1000;

    /**
     * url md5 加密并拼接参数
     *
     * @param url
     * @return
     */
    public String operatorUrl(String url) {
        if (url.endsWith("?") || url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        // 获取md5 加密字符串
        String md5Str = UrlEncode(url);

        TokenCache.setKey(TokenCache.TOKEN_PREFIX + md5Str, url);

        String jointStr = url.contains("?") ? "&" : "?";
        String returnUrl = new StringBuffer(url).append(jointStr).append("token=" + md5Str).toString();

        return returnUrl;
    }

    /**
     * url 验证
     *
     * @param token
     * @return
     */
    public Integer validate(String token) {
        if (StringUtils.isBlank(token)) {
            log.info("参数错误，token为空");
            return 500;
        }

        String localUrl = TokenCache.getKey(TokenCache.TOKEN_PREFIX + token);
        List<String> url = StringUtil.splitToListStr(passUrl);
        if (url.contains(localUrl)) {
            return 200;
        }

        if (StringUtils.isNotBlank(localUrl)) {
            String sign = UrlEncode(localUrl);

            if (sign.equals(token)) {
                log.info("验证通过");
                return 200;
            } else {
                log.info("非法请求");
                return 500;
            }
        } else {
            return 500;
        }

    }

    /**
     * 加密字符串
     *
     * @param url
     * @return
     */
    private String UrlEncode(String url) {
        // 原始字符串
        String originalStr = url + getKey();
        // md5 加密
        String md5Str = MD5Util.encrypt(originalStr);

        return md5Str;
    }
}
