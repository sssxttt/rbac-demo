package top.xiaomingxing.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.xiaomingxing.utils.RedisUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class ValidTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${custom.config.jwt-secret}")
    private String jwtSecret;
    @Value("${custom.config.redis-prefix}")
    private String redisTokenPrefix;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token 验证
        if (!request.getRequestURI().equals("/login")) {
            // 从请求头中获取 token
            String headerToken = request.getHeader("x-token");
            if (StringUtils.isNotBlank(headerToken)) {
                // 验证 token 的合法性
                if (JWTUtil.verify(headerToken, jwtSecret.getBytes(StandardCharsets.UTF_8))) {

                    // 从获取的请求头中解析 userId 为后续获取 redis 缓存中的 token 做准备
                    Object userId =  JWTUtil.parseToken(headerToken).getPayload().getClaim("userId");

                    if (Objects.isNull(userId)) {
                        throw new BadCredentialsException("token 格式错误");
                    }

                    // 判断 token 是否过期
                    String cacheToken = redisUtil.get(redisTokenPrefix + userId);
                    if (StringUtils.isNotBlank(cacheToken)) {
                        // 解析 token
                        JWT jwt = JWTUtil.parseToken(cacheToken);
                        String username = (String) jwt.getPayload().getClaim("username");
                        // 查询当前用户信息
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // 将用户详情信息放入 Spring Security 上下文中
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    } else {
                        throw new BadCredentialsException("Token 已过期");
                    }
                } else {
                    throw new BadCredentialsException("非法 Token");
                }
            }
        }

        doFilter(request, response, filterChain);
    }
}
