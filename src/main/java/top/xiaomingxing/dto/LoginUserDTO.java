package top.xiaomingxing.dto;

import lombok.Data;

/**
 * 登录用户数据传输类
 */
@Data
public class LoginUserDTO {
    private String userId;
    private String username;
    private String avatar;
    private String token;
    private String introduction;
}
