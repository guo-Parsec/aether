package top.finder.aether.data.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>基础数据实体</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
@Setter
@Getter
public abstract class BaseDataEntity extends BaseEntity {
    private static final long serialVersionUID = 6125581009488770858L;

    /**
     * 数据删除时间(未删除时为0)
     */
    @TableLogic(value = "0", delval = "1")
    protected Long deleteAt;

    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime gmtCreate;

    /**
     * 数据修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime gmtModify;

    public BaseDataEntity() {
    }

    public BaseDataEntity(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseDataEntity.class.getSimpleName() + "[", "]")
                .add("deleteAt='" + deleteAt + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}
