package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.data.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>操作历史记录表</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@ApiModel(value = "操作历史记录表")
@Getter
@Setter
@TableName(value = "ams_hi_operate")
public class HiOperateLog extends BaseEntity {
    private static final long serialVersionUID = 2441343986188659759L;
    private static final Logger log = LoggerFactory.getLogger(HiOperateLog.class);
    /**
     * 访问路径
     */
    @TableField(value = "access_url")
    @ApiModelProperty(value = "访问路径")
    protected String accessUrl;

    /**
     * 访问应用
     */
    @TableField(value = "access_app")
    @ApiModelProperty(value = "访问应用")
    protected String accessApp;

    /**
     * 操作人id
     */
    @TableField(value = "operate_user_id")
    @ApiModelProperty(value = "操作人id")
    protected Long operateUserId;

    /**
     * 操作用户账户
     */
    @TableField(value = "operate_user_account")
    @ApiModelProperty(value = "操作用户账户")
    protected String operateUserAccount;

    /**
     * 操作ip
     */
    @TableField(value = "operate_ip")
    @ApiModelProperty(value = "操作ip")
    protected String operateIp;

    /**
     * 设备名称
     */
    @TableField(value = "device_name")
    @ApiModelProperty(value = "设备名称")
    protected String deviceName;

    /**
     * 耗时时间(ms)
     */
    @TableField(value = "time_consuming")
    @ApiModelProperty(value = "耗时时间(ms)")
    protected Long timeConsuming;

    /**
     * 访问类型
     */
    @TableField(value = "method_type")
    @ApiModelProperty(value = "访问类型")
    protected String methodType;

    /**
     * 访问结果
     */
    @TableField(value = "access_result")
    @ApiModelProperty(value = "访问结果")
    protected String accessResult;

    /**
     * 错误码
     */
    @TableField(value = "error_code")
    @ApiModelProperty(value = "错误码")
    protected Integer errorCode;

    /**
     * 错误信息
     */
    @TableField(value = "error_message")
    @ApiModelProperty(value = "错误信息")
    protected String errorMessage;

    /**
     * 日志记录时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "日志记录时间")
    protected LocalDateTime gmtCreate;

    /**
     * <p>logModel转化为HiOperateLog</p>
     *
     * @param systemLogInfo 日志模型
     * @return {@link HiOperateLog}
     * @author guocq
     * @date 2022/12/30 11:33
     */
    public static HiOperateLog transformToHiOperateLog(SystemLogInfo systemLogInfo) {
        HiOperateLog opLog = new HiOperateLog();
        opLog.setAccessUrl(systemLogInfo.getAccessUrl());
        opLog.setAccessApp(systemLogInfo.getAccessApp());
        opLog.setOperateUserId(systemLogInfo.getUserId());
        opLog.setOperateUserAccount(systemLogInfo.getUserAccount());
        opLog.setOperateIp(systemLogInfo.getIp());
        opLog.setDeviceName(systemLogInfo.getDeviceName());
        opLog.setTimeConsuming(systemLogInfo.getTimeConsuming());
        opLog.setMethodType(systemLogInfo.getMethodType());
        opLog.setAccessResult(systemLogInfo.getResult());
        opLog.setErrorCode(systemLogInfo.getErrorCode());
        opLog.setErrorMessage(systemLogInfo.getErrorMessage());
        opLog.setGmtCreate(systemLogInfo.getLogTime());
        log.debug("转换后的类型为{}", opLog);
        return opLog;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HiOperateLog.class.getSimpleName() + "[", "]")
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