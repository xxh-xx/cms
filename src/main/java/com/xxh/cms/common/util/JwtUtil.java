package com.xxh.cms.common.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

/**
 * @author xxh
 */
public class JwtUtil {

    private final static String SECRET = "!@#xxh$%^";

    public static String createToken(Map<String,Object> info){

        String token = JWT.create()
                          .withClaim("user", info)
                          .withExpiresAt(DateUtil.nextWeek())
                          .sign(Algorithm.HMAC256(SECRET));

        return token;

    }

    public static boolean verifyToken(String token){

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

        try {
            verifier.verify(token);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Map<String, Object> getClaim(String token){

        if (verifyToken(token)){
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim("user").asMap();
        }

        return null;
    }



}
