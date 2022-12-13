package top.xiaomingxing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.service.SysUserService;
import top.xiaomingxing.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomingxing
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-12-14 02:26:55
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




