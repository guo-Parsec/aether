package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.IPageDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * <p>系统参数分页查询</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Getter
@Setter
@ApiModel("系统参数分页查询")
public class SysSysParamPageQueryDto extends SysParamQueryDto implements IPageDto {
    private static final long serialVersionUID = 9113649022922905946L;
    /**
     * 当前页
     */
    @NotNull(message = "分页查询时当前页不能为空")
    @Min(value = 1, message = "分页查询时当前页不能小于1")
    @ApiModelProperty("当前页")
    private Integer currentPage;

    /**
     * 页大小
     */
    @NotNull(message = "分页查询时页大小不能为空")
    @ApiModelProperty("页大小")
    @Min(value = 1, message = "分页查询时页大小不能小于1")
    private Integer pageSize;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysSysParamPageQueryDto.class.getSimpleName() + "[", "]")
                .add("currentPage=" + currentPage)
                .add("pageSize=" + pageSize)
                .add("id=" + id)
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .toString();
    }
}
