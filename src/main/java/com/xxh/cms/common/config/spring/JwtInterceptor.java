package com.xxh.cms.common.config.spring;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.JwtUtil;
import com.xxh.cms.users.common.TokenCache;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xxh
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String OPTIONS = HttpMethod.OPTIONS.toString();

        if (OPTIONS.equals(request.getMethod())){
            return true;
        }

        String token = request.getHeader("X-Token");

        if (!StrUtil.hasBlank(token)){
            if (JwtUtil.verifyToken(token)){
                String userId = TokenCache.get(JwtUtil.getClaim(token).get("userId").toString());
                if (!StrUtil.hasBlank(userId)){
                    return true;
                }
            }
        }

        response.setContentType("application/json; charset=UTF-8");
        String result = JSON.toJSONString(Result.failure(ResultCode.TOKEN_ERROR));
        response.getWriter().println(result);
        return false;
    }

}
