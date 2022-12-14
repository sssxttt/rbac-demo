package top.xiaomingxing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.mapper.SysUserMapper;
import top.xiaomingxing.service.SysUserService;

import java.util.List;

/**
* @author xiaomingxing
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-12-14 02:26:55
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysRole> getRolesById(Long id) {
        return sysUserMapper.getRolesById(id);
    }
}




