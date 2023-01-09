package top.finder.aether.base.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseVo;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>操作历史记录数据展示层</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@ApiModel(value = "操作历史记录")
@Getter
@Setter
public class HiOperateLogVo extends BaseVo {
    private static final long serialVersionUID = 2441343986188659759L;
    /**
     * 访问路径
     */
    @ApiModelProperty(value = "访问路径")
    private String accessUrl;

    /**
     * 访问应用
     */
    @ApiModelProperty(value = "访问应用")
    private String accessApp;

    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    private Long operateUserId;

    /**
     * 操作用户账户
     */
    @ApiModelProperty(value = "操作用户账户")
    private String operateUserAccount;

    /**
     * 操作ip
     */
    @ApiModelProperty(value = "操作ip")
    private String operateIp;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /**
     * 耗时时间(ms)
     */
    @ApiModelProperty(value = "耗时时间(ms)")
    private Long timeConsuming;

    /**
     * 访问类型
     */
    @ApiModelProperty(value = "访问类型")
    private String methodType;

    /**
     * 访问结果
     */
    @ApiModelProperty(value = "访问结果")
    private String accessResult;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private Integer errorCode;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorMessage;

    /**
     * 日志记录时间
     */
    @ApiModelProperty(value = "日志记录时间")
    private LocalDateTime gmtCreate;

    @Override
    public String toString() {
        return new StringJoiner(", ", HiOperateLogVo.class.getSimpleName() + "[", "]")
                .add("accessUrl='" + accessUrl + "'")
                .add("accessApp='" + accessApp + "'")
                .add("operateUserId=" + operateUserId)
                .add("operateUserAccount='" + operateUserAccount + "'")
                .add("operateIp='" + operateIp + "'")
                .add("deviceName='" + deviceName + "'")
                .add("timeConsuming=" + timeConsuming)
                .add("methodType=" + methodType)
                .add("accessResult=" + accessResult)
                .add("errorCode=" + errorCode)
                .add("errorMessage='" + errorMessage + "'")
                .add("id=" + id)
                .add("gmtCreate=" + gmtCreate)
                .toString();
    }
}