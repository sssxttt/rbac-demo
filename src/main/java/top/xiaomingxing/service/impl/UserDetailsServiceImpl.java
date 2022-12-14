package top.xiaomingxing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.xiaomingxing.entity.SysMenu;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.service.SysUserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询当前用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);

        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 根据ID来查询当前用户的角色信息
        List<SysRole> roles = sysUserService.getRolesById(sysUser.getId());
        sysUser.setRoles(roles);

        // 根据当前ID查询用户权限
        List<SysMenu> menus = sysUserService.getMenusById(sysUser.getId());
        sysUser.setMenus(menus);
        List<String> collect = menus.stream().filter(Objects::nonNull)
                .map(SysMenu::getCode)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(collect.toArray(new String[collect.size()]));
        sysUser.setAuthorities(authorityList);

        return sysUser;
    }
}
