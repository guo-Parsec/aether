package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.data.common.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>登录历史记录表</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@ApiModel(value = "登录历史记录表")
@Getter
@Setter
@TableName(value = "ams_hi_login")
public class HiLogin extends BaseEntity {
    private static final long serialVersionUID = -4793960348559654539L;
    private static final Logger log = LoggerFactory.getLogger(HiLogin.class);
    /**
     * 登录ip
     */
    @TableField(value = "login_ip")
    @ApiModelProperty(value = "登录ip")
    protected String loginIp;

    /**
     * 登录账户
     */
    @TableField(value = "login_account")
    @ApiModelProperty(value = "登录账户")
    protected String loginAccount;

    /**
     * 登录是否成功 0-成功 1-失败
     */
    @TableField(value = "login_result")
    @ApiModelProperty(value = "登录是否成功")
    protected String loginResult;

    /**
     * 登录设备
     */
    @TableField(value = "device_name")
    @ApiModelProperty(value = "登录设备")
    protected String deviceName;

    /**
     * 耗时时间(ms)
     */
    @TableField(value = "time_consuming")
    @ApiModelProperty(value = "耗时时间(ms)")
    protected Long timeConsuming;

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
     * <p>logModel转化为HiLogin</p>
     *
     * @param logModel 日志模型
     * @return {@link HiLogin}
     * @author guocq
     * @date 2023/01/04 10:26
     */
    public static HiLogin transformToHiLoginLog(LogModel logModel) {
        HiLogin loginLog = new HiLogin();
        loginLog.setLoginIp(logModel.getIp());
        loginLog.setLoginAccount(logModel.getUserAccount());
        loginLog.setLoginResult(logModel.getResult());
        loginLog.setDeviceName(logModel.getDeviceName());
        loginLog.setTimeConsuming(logModel.getTimeConsuming());
        loginLog.setErrorCode(logModel.getErrorCode());
        loginLog.setErrorMessage(logModel.getErrorMessage());
        loginLog.setGmtCreate(logModel.getLogTime());
        log.debug("转换后的类型为{}", loginLog);
        return loginLog;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HiLogin.class.getSimpleName() + "[", "]")
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