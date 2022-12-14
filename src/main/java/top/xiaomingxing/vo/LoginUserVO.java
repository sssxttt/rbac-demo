package top.xiaomingxing.vo;

import lombok.Data;

/**
 * 前端展示数据实体类
 */
@Data
public class LoginUserVO {
    private String username;
    private String avatar;
    private String introduction;
    private String[] roles;
}
