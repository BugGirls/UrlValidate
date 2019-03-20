package com.hndt.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 * @author Hystar
 */
@Slf4j
public class TokenCache2 {

    public static final String TOKEN_PREFIX = "token_";

    // LRU算法
    public static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            // 有效期，2天
            .expireAfterAccess(5, TimeUnit.DAYS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载.
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        } catch (Exception e){
            log.error("localCache get error",e);
        }
        return null;
    }

    public static void main(String[] args) {
        TokenCache2.setKey(TokenCache2.TOKEN_PREFIX, "123");
        String localUrl = TokenCache2.getKey(TokenCache2.TOKEN_PREFIX);
        System.out.println(localUrl);
    }
}
