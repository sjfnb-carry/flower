package com.mall.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.handler.GlobalException;
import com.mall.common.handler.GlobalExceptionEnum;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {


    private static final String JWT_SECERT = "test_jwt_secert" ;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static SecretKey generalKey() {
        try {
            // byte[] encodedKey = Base64.decode(JWT_SECERT);
            // 不管哪种方式最终得到一个byte[]类型的key就行
            byte[] encodedKey = JWT_SECERT.getBytes("UTF-8");
            SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 签发JWT，创建token的方法。
     * id  jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     * iss jwt签发者
     * subject jwt所面向的用户。payload中记录的public claims。当前环境中就是用户的登录名。
     * ttlMillis 有效期,单位毫秒
     * token是一次性的。是为一个用户的有效登录周期准备的一个token。用户退出或超时，token失效。
     *
     */
    public static String createJWT(String id,String iss, String subject, long ttlMillis) {
        // 加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间。
        long nowMillis = System.currentTimeMillis();
        // 当前时间的日期对象。
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        // 创建JWT的构建器。 就是使用指定的信息和加密算法，生成Token的工具。
        JwtBuilder builder = Jwts.builder()
                .setId(id)  // 设置身份标志。就是一个客户端的唯一标记。 如：可以使用用户的主键，客户端的IP，服务器生成的随机数据。
                .setIssuer(iss)
                .setSubject(subject)
                .setIssuedAt(now) // token生成的时间。
                .signWith(signatureAlgorithm, secretKey); // 设定密匙和算法
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis); // token的失效时间。
            builder.setExpiration(expDate);
        }
        return builder.compact(); // 生成token
    }

    /*验证JWT */
    public static Claims validateJWT(String jwtStr) {
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
        } catch (ExpiredJwtException e) { // token超时
           throw new GlobalException(GlobalExceptionEnum.TOKEN_INVALIDATION.getCode(),GlobalExceptionEnum.TOKEN_INVALIDATION.getMessage());
        } catch (SignatureException e) { // 校验失败
            throw new GlobalException(GlobalExceptionEnum.TOKEN_AUTHENTICATION_FAILED.getCode(),"token认证失败");
        } catch (Exception e) {
            throw new GlobalException(GlobalExceptionEnum.UNKNOWN_REASON.getCode(),"未知错误");
        }

        return claims;
    }

    /*解析JWT字符串，就是服务器为客户端生成的签名数据，就是token。*/
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody(); // getBody获取的就是token中记录的payload数据。就是payload中保存的所有的claims。
    }

    /*生成subject信息*/
    public static String generalSubject(Object subObj){
        try {
            return MAPPER.writeValueAsString(subObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}