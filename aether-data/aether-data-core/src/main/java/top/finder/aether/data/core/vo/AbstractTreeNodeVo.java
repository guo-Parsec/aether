package top.finder.aether.data.core.vo;

import top.finder.aether.common.vo.IVo;
import top.finder.aether.data.core.entity.AbstractTreeNode;

import java.util.StringJoiner;

/**
 * <p>树节点vo抽象类</p>
 *
 * @author guocq
 * @since 2023/1/18
 */
public abstract class AbstractTreeNodeVo extends AbstractTreeNode<Long> implements IVo {
    private static final long serialVersionUID = 1935913238848982143L;
    
    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractTreeNodeVo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("parentId=" + parentId)
                .add("children=" + children)
                .toString();
    }
}
