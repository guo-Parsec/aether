package top.finder.aether.data.core.entity;

import top.finder.aether.common.model.IModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>树节点接口</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface ITreeNode<K extends Serializable> extends IModel {
    /**
     * <p>获取主键</p>
     *
     * @return K 主键
     * @author guocq
     * @date 2023/1/16 16:49
     */
    K getId();

    /**
     * <p>获取父级主键</p>
     *
     * @return K 父级主键
     * @author guocq
     * @date 2023/1/16 16:49
     */
    K getParentId();

    /**
     * <p>设置子元素集合</p>
     *
     * @param children 子元素集合
     * @author guocq
     * @date 2023/1/16 16:50
     */
    void setChildren(Collection<ITreeNode<K>> children);

    /**
     * <p>获取子元素集合</p>
     *
     * @return {@link Collection}
     * @author guocq
     * @date 2023/1/16 16:54
     */
    Collection<? extends ITreeNode<K>> getChildren();
}
