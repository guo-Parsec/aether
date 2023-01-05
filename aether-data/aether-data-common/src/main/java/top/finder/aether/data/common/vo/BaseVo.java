package top.finder.aether.data.common.vo;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.vo.IVo;

import java.util.StringJoiner;

/**
 * <p>基础数据展示层实体</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
@Setter
@Getter
public abstract class BaseVo implements IVo {
    private static final long serialVersionUID = -799773759310003514L;

    /**
     * 主键
     */
    protected String id;

    public BaseVo() {
    }

    public BaseVo(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseVo.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .toString();
    }
}
