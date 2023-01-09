package top.finder.aether.base.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseVo;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>登录历史记录数据展示层</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@ApiModel(value="登录历史记录")
@Getter
@Setter
public class HiLoginVo extends BaseVo {
    private static final long serialVersionUID = -4793960348559654539L;

    /**
     * 登录ip
     */
    @ApiModelProperty(value="登录ip")
    private String loginIp;

    /**
     * 登录账户
     */
    @ApiModelProperty(value="登录账户")
    private String loginAccount;

    /**
     * 登录是否成功 0-成功 1-失败
     */
    @ApiModelProperty(value="登录是否成功")
    private String loginResult;

    /**
     * 登录设备
     */
    @ApiModelProperty(value="登录设备")
    private String deviceName;

    /**
     * 耗时时间(ms)
     */
    @ApiModelProperty(value="耗时时间(ms)")
    private Long timeConsuming;

    /**
     * 错误码
     */
    @ApiModelProperty(value="错误码")
    private Integer errorCode;

    /**
     * 错误信息
     */
    @ApiModelProperty(value="错误信息")
    private String errorMessage;

    /**
     * 日志记录时间
     */
    @ApiModelProperty(value = "日志记录时间")
    private LocalDateTime gmtCreate;

    @Override
    public String toString() {
        return new StringJoiner(", ", HiLoginVo.class.getSimpleName() + "[", "]")
                .add("loginIp='" + loginIp + "'")
                .add("loginAccount='" + loginAccount + "'")
                .add("loginResult='" + loginResult + "'")
                .add("deviceName='" + deviceName + "'")
                .add("timeConsuming=" + timeConsuming)
                .add("errorCode=" + errorCode)
                .add("errorMessage='" + errorMessage + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("id='" + id + "'")
                .toString();
    }
}