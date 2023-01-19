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
 * <p>用户分页查询参数</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Getter
@Setter
@ApiModel("用户分页查询参数")
public class SysUserPageQueryDto extends SysUserQueryDto implements IPageDto {

    private static final long serialVersionUID = -3323511371707903871L;
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
        return new StringJoiner(", ", SysUserPageQueryDto.class.getSimpleName() + "[", "]")
                .add("currentPage=" + currentPage)
                .add("pageSize=" + pageSize)
                .add("account='" + account + "'")
                .add("nickname='" + nickname + "'")
                .add("sexSet=" + sexSet)
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthdayStarter=" + birthdayStarter)
                .add("birthdayEnd=" + birthdayEnd)
                .add("userTypeSet=" + userTypeSet)
                .toString();
    }
}
