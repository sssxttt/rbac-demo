package top.xiaomingxing.handler;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.xiaomingxing.dto.LoginUserDTO;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.response.ResponseResult;
import top.xiaomingxing.utils.RedisUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

/**
 * 认证成功处理器
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisUtil redisUtil;
    @Value("${custom.config.jwt-secret}")
    private String jwtSecret;
    @Value("${custom.config.redis-prefix}")
    private String redisTokenPrefix;
    private long expireTime = 1000 * 60 * 60;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;");

        // 返回认证成功的信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        // 签发 token
        Long userId = sysUser.getId();
        String username = sysUser.getUsername();

        String token = JWTUtil.createToken(new HashMap<String, Object>() {
            {
                put("userId", userId);
                put("username", username);
                put("expireTime", expireTime);
                put("now", new Date());
            }
        }, jwtSecret.getBytes(StandardCharsets.UTF_8));

        // 存入 redis 缓存中
        redisUtil.set(redisTokenPrefix + userId, token, expireTime);

        // 创建数据传输类
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setToken(token);


        // 返回 json 数据
        String result = JSON.toJSONString(new ResponseResult<Object>().ok("登录成功", loginUserDTO), SerializerFeature.DisableCircularReferenceDetect);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
