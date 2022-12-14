package top.xiaomingxing;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BaseTest {

    @Test
    public void test() {
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("2", "1", "菜单管理", 0));
        nodeList.add(new TreeNode<>("3", "1", "角色管理", 0));
        nodeList.add(new TreeNode<>("4", "1", "用户管理", 0));

        // 0表示最顶层的id是0
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        String res = JSON.toJSONString(treeList, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(res);

    }


}
