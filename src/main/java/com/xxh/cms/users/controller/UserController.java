package com.xxh.cms.users.controller;

import cn.hutool.core.util.StrUtil;
import com.xxh.cms.common.resultUtil.Result;
import com.xxh.cms.common.resultUtil.ResultCode;
import com.xxh.cms.common.util.JwtUtil;
import com.xxh.cms.users.entity.User;
import org.springframework.web.bind.annotation.*;

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

        Map<String,String> result = new HashMap<>(1);
        result.put("user_id","1");

        String token = JwtUtil.createToken(result);

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

        Map<String,String> result = new HashMap<>(2);
        result.put("name","xxh");
        result.put("avatar","https://ae01.alicdn.com/kf/Ufe669619876446adacfe991725c742dfe.jpg");

        return Result.success(result);
    }

}
