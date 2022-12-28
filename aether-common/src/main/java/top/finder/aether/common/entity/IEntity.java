package top.finder.aether.common.entity;

import top.finder.aether.common.model.IModel;

/**
 * <p>基础模型类统一接口</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
public interface IEntity extends IModel {
    /**
     * <p>获取数据主键</p>
     *
     * @return I 数据主键类型
     * @author guocq
     * @date 2022/12/12 19:10
     */
    Long getId();
}
