package top.xiaomingxing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xiaomingxing.entity.SysMenu;
import top.xiaomingxing.service.SysMenuService;
import top.xiaomingxing.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaomingxing
* @description 针对表【sys_menu(菜单表（权限表）)】的数据库操作Service实现
* @createDate 2022-12-14 02:42:10
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

}




