package top.xiaomingxing.handler;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.response.ResponseResult;
import top.xiaomingxing.utils.RedisUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义退出处理器
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtil redisUtil;
    @Value("${custom.config.redis-prefix}")
    private String redisTokenPrefix;
    @Value("${custom.config.jwt-secret}")
    private String jwtSecret;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;");

        try {
            // 获取 token
            // 从请求头中获取 token
            String headerToken = request.getHeader("x-token");
            // 从请求参数中获取 token
            String requestToken = request.getParameter("token");
            // 从 cookie 中获取 token
            String cookieToken = null;
            Cookie[] cookies = request.getCookies();
            if (Objects.nonNull(cookies) && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        cookieToken = cookie.getValue();
                    }
                }
            }

            String token = null;
            List<String> arrayList = new ArrayList<>();
            arrayList.add(headerToken);
            arrayList.add(requestToken);
            arrayList.add(cookieToken);
            Set<String> collect = arrayList.stream().filter(item -> item != null).collect(Collectors.toSet());
            if (Objects.isNull(collect)) {
                throw new BadCredentialsException("未携带 token");
            }
            if (collect.size() > 1) {
                throw new BadCredentialsException("token 不一致");
            }
            Iterator<String> it = collect.iterator();
            token = it.next();

            if (StringUtils.isBlank(token)) {
                throw new BadCredentialsException("未携带 token");
            }

            if (!JWTUtil.verify(token, jwtSecret.getBytes())) {
                throw new BadCredentialsException("非法 token");
            }


            JWT jwt = JWTUtil.parseToken(token);
            Object userId = jwt.getPayload().getClaim("userId");

            // 清除 redis 中的 token 值
            redisUtil.del(redisTokenPrefix + userId);
            // 清除上下文中的登录信息
            SecurityContextHolder.clearContext();

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONString(new ResponseResult().ok("退出成功"), SerializerFeature.DisableCircularReferenceDetect).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

        } catch (BadCredentialsException e) {

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONString(new ResponseResult().ok("退出失败"), SerializerFeature.DisableCircularReferenceDetect).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

            throw new RuntimeException(e);
        }




    }
}
