package com.xxh.cms.common.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;
import java.util.Set;

/**
 * @author xxh
 */
public class JwtUtil {

    private final static String SECRET = "!@#xxh$%^";

    public static String createToken(Map<String,String> info){

        JWTCreator.Builder builder = JWT.create();
        builder.withExpiresAt(DateUtil.nextWeek());
        Set<String> keySet = info.keySet();
        for (String key : keySet){
            builder.withClaim(key,info.get(key));
        }

        return builder.sign(Algorithm.HMAC256(SECRET));

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

    public static String getClaim(String token,String name){

        DecodedJWT decode = JWT.decode(token);

        return decode.getClaim(name).asString();
    }



}
