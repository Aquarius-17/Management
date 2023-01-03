package com.example.system.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.system.entity.Role;

import javax.xml.crypto.Data;
import java.util.Date;

public class TokenUtils {


    public static String genToken(Role role) {


        String token = "";
        token = JWT.create().withAudience(role.getRoleId()) // 将 user id 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2H后token过期
                .sign(Algorithm.HMAC256(role.getPassword())); // 以 password 作为 token 的密钥

        return token;

    }
}
