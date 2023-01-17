package top.finder.aether.data.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * <p>树结构数据节点</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Setter
@Getter
public abstract class AbstractTreeNode implements ITreeNode<Long> {
    private static final long serialVersionUID = 8536099298974979986L;

    /**
     * 主键
     */
    protected Long id;

    /**
     * 父级id
     */
    protected Long parentId;

    /**
     * 子元素id
     */
    protected Collection<ITreeNode<Long>> children;
}
