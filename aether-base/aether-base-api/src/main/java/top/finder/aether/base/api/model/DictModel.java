package top.finder.aether.base.api.model;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import java.util.StringJoiner;

/**
 * <p>数据字典模型</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@Setter
@Getter
public class DictModel implements IModel {
    private static final long serialVersionUID = -5642650500913427861L;

    /**
     * 字典码
     */
    private String code;

    /**
     * 字典值
     */
    private Integer value;

    public DictModel() {
    }

    public DictModel(String code) {
        this.code = code;
    }

    public DictModel(Integer value) {
        this.value = value;
    }

    public DictModel(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public static DictModel of(String code, Integer value) {
        return new DictModel(code, value);
    }

    public static DictModel of(Integer value) {
        return new DictModel(value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DictModel.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .add("value=" + value)
                .toString();
    }
}
