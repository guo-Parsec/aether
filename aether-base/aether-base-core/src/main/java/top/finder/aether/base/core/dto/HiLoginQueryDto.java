package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.core.entity.HiLogin;
import top.finder.aether.data.core.entity.IPageDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * <p>登录日志分页查询参数</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@Getter
@Setter
@ApiModel("登录日志分页查询参数")
public class HiLoginQueryDto extends HiLogin implements IPageDto {
    private static final long serialVersionUID = 4443588900807915181L;
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
        return new StringJoiner(", ", HiLoginQueryDto.class.getSimpleName() + "[", "]")
                .add("currentPage=" + currentPage)
                .add("pageSize=" + pageSize)
                .add("loginIp='" + loginIp + "'")
                .add("loginAccount='" + loginAccount + "'")
                .add("loginResult='" + loginResult + "'")
                .add("deviceName='" + deviceName + "'")
                .add("timeConsuming=" + timeConsuming)
                .add("errorCode=" + errorCode)
                .add("errorMessage='" + errorMessage + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("id=" + id)
                .toString();
    }
}
