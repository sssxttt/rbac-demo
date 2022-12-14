package top.xiaomingxing.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xiaomingxing.entity.SysRole;
import top.xiaomingxing.entity.SysUser;

import java.util.List;

/**
* @author xiaomingxing
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-12-14 02:26:55
* @Entity top.xiaomingxing.entity.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysRole> getRolesById(Long id);

}




