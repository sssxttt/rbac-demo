package top.xiaomingxing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.response.ResponseResult;
import top.xiaomingxing.service.SysUserService;
import top.xiaomingxing.vo.LoginUserVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/sys-user")
public class SysUserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/{id}")
    public ResponseResult getSysUserById(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            return new ResponseResult().fail("参数错误");
        }
        SysUser sysUser = sysUserService.getById(id);
        return new ResponseResult().ok(sysUser);
    }

    @PostMapping("/page")
    public ResponseResult getSysUserByPage(@RequestParam(defaultValue = "1") long current, @RequestParam(defaultValue = "10") long size) {
        Page<SysUser> sysUserPage = new Page<>(current, size);
        sysUserService.page(sysUserPage);
        return new ResponseResult().ok(sysUserPage);
    }

    @PostMapping("/add")
    public ResponseResult addSysUser(@RequestBody SysUser sysUser) {
        if (Objects.isNull(sysUser)) {
            return new ResponseResult().fail("参数错误");
        }

        // 对密码进行加密
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        if (sysUserService.save(sysUser)) {
            return new ResponseResult().ok("添加成功");
        }
        return new ResponseResult().fail("添加失败");
    }



    @PostMapping("/del")
    public ResponseResult deleteSysUserById(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResponseResult().fail("参数错误");
        }

        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(id));
        if (sysUserService.removeById(sysUser)) {
            return new ResponseResult().ok("删除成功");
        }
        return new ResponseResult().fail("删除失败");
    }


    @PostMapping("/update")
    public ResponseResult deleteSysUserById(@RequestBody SysUser sysUser) {
        if (Objects.isNull(sysUser) || StringUtils.isBlank(String.valueOf(sysUser.getId()))) {
            return new ResponseResult().fail("参数错误");
        }
        if (sysUserService.updateById(sysUser)) {
            return new ResponseResult().ok("更新成功");
        }
        return new ResponseResult().fail("更新失败");
    }

    @GetMapping("/getSysUserInfo")
    public ResponseResult getSysUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUsername(sysUser.getUsername());
        loginUserVO.setAvatar(sysUser.getAvatar());
        loginUserVO.setIntroduction(sysUser.getIntroduction());
        List<SysRole> roles = sysUser.getRoles();
        List<String> collect = roles.stream().filter(Objects::nonNull).map(SysRole::getRoleCode).collect(Collectors.toList());
        loginUserVO.setRoles(collect.toArray(new String[collect.size()]));

        return new ResponseResult().ok(loginUserVO);
    }


/*    @DeleteMapping("/{id}")
    public ResponseResult deleteSysUserById(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            return new ResponseResult().fail("参数错误");
        }

        if (sysUserService.removeById(id)) {
            return new ResponseResult().ok("删除成功");
        }
        return new ResponseResult().fail("删除失败");
    }


    @PutMapping("/{id}")
    public ResponseResult deleteSysUserById(@PathVariable("id") String id, @RequestBody SysUser sysUser) {
        if (StringUtils.isBlank(id) || Objects.isNull(sysUser)) {
            return new ResponseResult().fail("参数错误");
        }
        if (sysUserService.updateById(sysUser)) {
            return new ResponseResult().ok("更新成功");
        }
        return new ResponseResult().fail("更新失败");
    }*/

}
