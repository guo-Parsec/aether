package top.finder.aether.data.core.vo;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>基础数据实体展示层实体</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
@Setter
@Getter
public abstract class BaseDataVo extends BaseVo {
    private static final long serialVersionUID = -409162548210869885L;

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

    public BaseDataVo() {
        super();
    }

    public BaseDataVo(Long id) {
        super(StrUtil.toString(id));
    }

    public BaseDataVo(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseDataVo.class.getSimpleName() + "[", "]")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id='" + id + "'")
                .toString();
    }
}
