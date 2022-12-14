package top.xiaomingxing.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable, UserDetails {
    /**
     * 用户ID（雪花算法）
     */
    @TableId
    private Long id;

    /**
     * 登录名（用户名）
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别：0 女、1 男
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 账号是否过期：0 已过期、1 未过期
     */
    private boolean isAccountNonExpired = true;

    /**
     * 账号是否被锁定：0 已锁定、1 未锁定
     */
    private boolean isAccountNonLocked = true;

    /**
     * 账号密码是否过期：0 已过期、1 未过期
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 账号是否是可用：0 禁用、1 可用
     */
    private boolean isEnabled = true;

    /**
     * 逻辑删除标志：0 未删除、1 已删除
     */
    @TableLogic
    private Integer isDelete = 0;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    /**
     * 用户所具有的角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 用户所具有的菜单集合
     */
    @TableField(exist = false)
    private List<SysMenu> menus;

    /**
     * 用户所具有的权限集合
     */
    @TableField(exist = false)
    private List<GrantedAuthority> authorities;

    /**
     * UserDetails 权限集合
     * 存放的是当前用户的权限编码
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", nickName=").append(nickName);
        sb.append(", realName=").append(realName);
        sb.append(", gender=").append(gender);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", isAccountNonExpired=").append(isAccountNonExpired);
        sb.append(", isAccountNonLocked=").append(isAccountNonLocked);
        sb.append(", isCredentialsNonExpired=").append(isCredentialsNonExpired);
        sb.append(", isEnabled=").append(isEnabled);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


}