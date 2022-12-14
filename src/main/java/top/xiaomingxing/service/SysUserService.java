package top.xiaomingxing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;

import java.util.List;

/**
* @author xiaomingxing
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-12-14 02:26:55
*/
public interface SysUserService extends IService<SysUser> {
    List<SysRole> getRolesById(Long id);
}
