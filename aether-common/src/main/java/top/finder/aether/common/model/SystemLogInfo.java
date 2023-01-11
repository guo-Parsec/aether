package top.finder.aether.common.model;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * <p>日志模型</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Setter
@Getter
public class SystemLogInfo implements IModel {
    private static final long serialVersionUID = -1560408447041120209L;

    /**
     * 访问url
     */
    private String accessUrl;

    /**
     * 访问app
     */
    private String accessApp;

    /**
     * 登录用户id
     */
    private Long userId;

    /**
     * 登录用户账户
     */
    private String userAccount;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 登录设备
     */
    private String deviceName;

    /**
     * 耗时时间
     */
    private Long timeConsuming;

    /**
     * 请求类型
     */
    private String methodType;

    /**
     * 结果类型
     */
    private String result;

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 记录时间
     */
    private LocalDateTime logTime;

    public void error(AetherException aetherException) {
        this.result = CommonConstantPool.FAILED;
        this.errorCode = aetherException.getCode();
        this.errorMessage = aetherException.getMessage();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SystemLogInfo.class.getSimpleName() + "[", "]")
                .add("accessUrl='" + accessUrl + "'")
                .add("accessApp='" + accessApp + "'")
                .add("userId=" + userId)
                .add("userAccount='" + userAccount + "'")
                .add("ip='" + ip + "'")
                .add("deviceName='" + deviceName + "'")
                .add("timeConsuming=" + timeConsuming)
                .add("methodType=" + methodType)
                .add("result=" + result)
                .add("errorCode='" + errorCode + "'")
                .add("errorMessage='" + errorMessage + "'")
                .add("logTime=" + logTime)
                .toString();
    }
}
