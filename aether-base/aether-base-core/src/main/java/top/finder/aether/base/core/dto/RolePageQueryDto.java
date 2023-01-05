package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.core.entity.Role;
import top.finder.aether.data.common.entity.IPageDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>角色查询参数</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Getter
@Setter
@ApiModel("角色分页查询参数")
public class RolePageQueryDto extends Role implements IPageDto {
    private static final long serialVersionUID = 4938421970914661532L;

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
}
