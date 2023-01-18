package top.finder.aether.data.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import top.finder.aether.common.vo.IVo;
import top.finder.aether.data.core.entity.AbstractTreeNode;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>树节点vo抽象类</p>
 *
 * @author guocq
 * @since 2023/1/18
 */
public abstract class AbstractTreeNodeVo extends AbstractTreeNode<Long> implements IVo {
    private static final long serialVersionUID = 1935913238848982143L;

    /**
     * 数据创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime gmtCreate;

    /**
     * 数据修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime gmtModify;

    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractTreeNodeVo.class.getSimpleName() + "[", "]")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .add("parentId=" + parentId)
                .add("children=" + children)
                .toString();
    }
}
