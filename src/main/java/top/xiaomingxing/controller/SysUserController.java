package top.xiaomingxing.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.xiaomingxing.entity.SysMenu;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.response.ResponseResult;
import top.xiaomingxing.service.SysUserService;
import top.xiaomingxing.vo.LoginUserVO;
import top.xiaomingxing.vo.RouterVO;

import java.util.*;
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


    @GetMapping("/getSysUserMenus")
    public ResponseResult getSysUserMenus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();

        // 获取用户对应的权限列表
        List<SysMenu> menus = sysUser.getMenus();
        // 过滤出菜单
        List<SysMenu> collect = menus.stream()
                .filter(item -> item != null && item.getType() != 2).collect(Collectors.toList());

        // 配置自定义树的属性
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setDeep(3);
        treeNodeConfig.setWeightKey("sort");

        // 生成树
        List<Tree<Long>> treeList = TreeUtil.build(collect, 0L, treeNodeConfig, (treeNode, tree) -> {
            // 设置属性
            tree.putExtra("id", treeNode.getId());
            tree.putExtra("label", treeNode.getLabel());
            tree.putExtra("parentId", treeNode.getParentId());
            tree.putExtra("parentLabel", treeNode.getParentLabel());
            tree.putExtra("code", treeNode.getCode());
            tree.putExtra("path", treeNode.getPath());
            tree.putExtra("name", treeNode.getName());
            tree.putExtra("url", treeNode.getUrl());
            tree.putExtra("type", treeNode.getType());
            tree.putExtra("icon", treeNode.getIcon());
            tree.putExtra("sort", treeNode.getSort());
//            tree.putExtra("remark", "");
        });

        return new ResponseResult().ok("请求成功", treeList);

    }


    @GetMapping("/getSysUserRouters")
    public ResponseResult getSysUserRouters() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) auth.getPrincipal();


        // 获取用户角色信息
        List<String> roles = sysUser.getRoles().stream()
                .filter(Objects::nonNull)
                .map(item -> item.getRoleCode())
                .collect(Collectors.toList());


        // 获取用户对应的权限列表
        List<SysMenu> menus = sysUser.getMenus();
        // 过滤出菜单
        List<SysMenu> collect = menus.stream()
                .filter(item -> item != null && item.getType() != 2).collect(Collectors.toList());


        // 构建路由树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setDeep(3);
        treeNodeConfig.setWeightKey("sort");


        // 生成树
        List<Tree<Long>> treeList = TreeUtil.build(collect, 0L, treeNodeConfig, (treeNode, tree) -> {
            // 给当前树结点填充属性值
            // 填充路由需要的信息
            tree.putExtra("id", treeNode.getId());
            tree.putExtra("parentId", treeNode.getParentId());

            // 组件URL路径
            tree.putExtra("path", treeNode.getUrl());

            if (treeNode.getType() == 0 && treeNode.getParentId() == 0) {
                // 组件文件路径
                tree.putExtra("component", "Layout");
            } else {
                // 组件文件路径
                tree.putExtra("component", treeNode.getPath());
            }


            // 组件名称
            tree.putExtra("name", treeNode.getName());
            // 组件元信息
            tree.putExtra("meta", new HashMap<String, Object>(){
                {
                    put("title", treeNode.getLabel());
                    put("icon", treeNode.getIcon());
                    put("roles", roles.toArray(new String[roles.size()]));
                }
            });
        });

        return  new ResponseResult<>().ok(treeList);

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
