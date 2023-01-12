package top.finder.aether.base.api.holders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

/**
 * <p>数据字典模型</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Setter
@Getter
@NoArgsConstructor
public class SysDictHolders implements IModel {
    private static final long serialVersionUID = -3868623617781745586L;
    /**
     * 字典类别码值
     */
    private String dictTypeCode;

    /**
     * 字典类别名称
     */
    private String dictTypeName;

    /**
     * 字典码值
     */
    private Integer dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    public SysDictHolders(String dictTypeCode, String dictTypeName, Integer dictCode, String dictName) {
        this.dictTypeCode = dictTypeCode;
        this.dictTypeName = dictTypeName;
        this.dictCode = dictCode;
        this.dictName = dictName;
    }
}
