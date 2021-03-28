package com.xxh.cms.users.controller;

import cn.hutool.core.util.StrUtil;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.JwtUtil;
import com.xxh.cms.users.common.TokenCache;
import com.xxh.cms.users.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxh
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public Result userLogin(@RequestBody User user){

        if (user==null){
            return Result.failure(ResultCode.LOGIN_ERROR);
        }

        String name = user.getUsername();
        String password = user.getPassword();
        if (StrUtil.hasBlank(name)||StrUtil.hasBlank(password)){
            return Result.failure(ResultCode.LOGIN_ERROR);
        }

        Map<String,Object> result = new HashMap<>(3);
        if (name.equals("admin")){
            result.put("userId","1");
            result.put("name","xxh");
            result.put("avatar","https://ae01.alicdn.com/kf/Ufe669619876446adacfe991725c742dfe.jpg");
            result.put("roles", Arrays.asList("admin"));
        }else {
            result.put("userId","2");
            result.put("name","hhh");
            result.put("avatar","https://ae01.alicdn.com/kf/Ufe669619876446adacfe991725c742dfe.jpg");
            result.put("roles", Arrays.asList("expert"));
        }

        String token = JwtUtil.createToken(result);

        TokenCache.add(result.get("userId").toString(),token);

        result = new HashMap<>(1);
        result.put("token",token);

        return Result.success(result);
    }

    @GetMapping("/getInfo")
    public Result getRole(String token){

        if (StrUtil.hasBlank(token)){
            return Result.failure(ResultCode.TOKEN_ERROR);
        }

        if (!JwtUtil.verifyToken(token)){
            return Result.failure(ResultCode.TOKEN_ERROR);
        }

        Map<String, Object> result = JwtUtil.getClaim(token);

        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){

        String token = request.getHeader("X-Token");
        if (!StrUtil.hasBlank(token)&&JwtUtil.verifyToken(token)){
            Map<String, Object> userMap = JwtUtil.getClaim(token);
            TokenCache.remove(userMap.get("userId").toString());
            return Result.success();
        }

        return Result.failure(ResultCode.TOKEN_ERROR);

    }

}
