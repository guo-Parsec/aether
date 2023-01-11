package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.api.model.DictModel;
import top.finder.aether.data.common.entity.BaseDataEntity;

import java.util.StringJoiner;

/**
 * <p>系统字典表</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Getter
@Setter
@TableName(value = "ams_dict")
public class Dict extends BaseDataEntity {
    private static final long serialVersionUID = 4389620667398620107L;
    /**
     * 字典类别码值
     */
    @TableField(value = "dict_type_code")
    private String dictTypeCode;

    /**
     * 字典类别名称
     */
    @TableField(value = "dict_type_name")
    private String dictTypeName;

    /**
     * 字典名称
     */
    @TableField(value = "dict_name")
    private String dictName;

    /**
     * 字典码值
     */
    @TableField(value = "dict_code")
    private Integer dictCode;

    /**
     * 字典排序
     */
    @TableField(value = "dict_sort")
    private Integer dictSort;

    /**
     * <p>当前对象转化为IDictModel</p>
     *
     * @return {@link DictModel}
     * @author guocq
     * @date 2022/12/29 14:17
     */
    public DictModel toDictModel() {
        return new DictModel(dictTypeCode, dictTypeName, dictCode, dictName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Dict.class.getSimpleName() + "[", "]")
                .add("dictTypeCode='" + dictTypeCode + "'")
                .add("dictTypeName='" + dictTypeName + "'")
                .add("dictName='" + dictName + "'")
                .add("dictCode='" + dictCode + "'")
                .add("dictSort=" + dictSort)
                .add("deleteAt='" + deleteAt + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}