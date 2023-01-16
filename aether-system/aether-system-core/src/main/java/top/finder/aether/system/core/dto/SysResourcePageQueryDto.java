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
 * <p>系统资源分页查询参数</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Setter
@Getter
@ApiModel("系统资源分页查询参数")
public class SysResourcePageQueryDto extends SysResourceQueryDto implements IPageDto {
    private static final long serialVersionUID = -2798489998185145523L;
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
        return new StringJoiner(", ", SysResourcePageQueryDto.class.getSimpleName() + "[", "]")
                .add("currentPage=" + currentPage)
                .add("pageSize=" + pageSize)
                .add("id=" + id)
                .add("resourceTypeCode='" + resourceTypeCode + "'")
                .add("resourceTypeName='" + resourceTypeName + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .toString();
    }
}
