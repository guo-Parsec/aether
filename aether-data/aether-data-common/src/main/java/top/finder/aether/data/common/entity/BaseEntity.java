package top.finder.aether.data.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.entity.IEntity;

import java.util.StringJoiner;

/**
 * <p>基础实体类</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
@KeySequence("snowflakeWorker")
public abstract class BaseEntity implements IEntity {
    private static final long serialVersionUID = 2086373323180874329L;

    /**
     * id主键
     */
    @Setter
    @Getter
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
