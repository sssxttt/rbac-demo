package top.xiaomingxing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 路由展示类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterVO {
    /**
     * 三个必要的属性值，用于生成路由树
     */
    private Long id;
    private Long parentId;
    private Integer sort;
    /**
     * 前端需要的数据
     */
    /**
     * 组件路径
     */
    private String component;
    /**
     * 组件访问URL
     */
    private String path;
    /**
     * 组件名称
     */
    private String name;
    /**
     * 组件附加信息
     */
    private Meta meta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Meta{
        private String title;
        private String icon;
        private String[] roles;
    }

    private List<RouterVO> children;

}
