package top.finder.aether.data.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>树结构数据</p>
 *
 * @author guocq
 * @since 2023/1/18
 */
@Setter
@Getter
public abstract class AbstractTreeNodeDataVo extends AbstractTreeNodeVo {
    private static final long serialVersionUID = 3877193714169475954L;

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
}
