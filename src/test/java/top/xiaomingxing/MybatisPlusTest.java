package top.xiaomingxing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.xiaomingxing.entity.SysUser;
import top.xiaomingxing.mapper.SysUserMapper;
import top.xiaomingxing.service.SysUserService;

import java.util.List;
@Slf4j

@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Test
    public void test1() {
        System.out.println(sysUserMapper.selectList(null));
    }

    @Test
    public void test2() {
        SysUser sysUser = sysUserService.getById("1");
        sysUser.setGender(1);
        sysUserService.updateById(sysUser);
    }

}
