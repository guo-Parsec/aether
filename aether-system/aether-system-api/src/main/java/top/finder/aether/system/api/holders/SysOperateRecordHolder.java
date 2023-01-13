package top.finder.aether.system.api.holders;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.Builder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringJoiner;

import static top.finder.aether.system.api.support.pool.SystemConstantPool.OPERATE_RESULT_FAILED;
import static top.finder.aether.system.api.support.pool.SystemConstantPool.OPERATE_RESULT_SUCCESS;

/**
 * <p>系统操作记录</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Getter
@Setter
public class SysOperateRecordHolder implements IModel {
    private static final long serialVersionUID = 5658981184336396476L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 操作人id
     */
    private Long operateId;

    /**
     * 操作人账户
     */
    private String operateAccount;

    /**
     * 操作结果
     */
    private Integer operateResult;

    /**
     * 操作结果码
     */
    private Integer operateCode;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 操作ip地址
     */
    private String operateIp;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    /**
     * 操作耗时(毫秒)
     */
    private Long timeSpent;

    /**
     * 操作uri
     */
    private String operateUri;

    /**
     * 操作类型
     */
    private String operateMethod;

    public static SysOperateRecordHolder buildRecordHolder(AetherException error, LocalDateTime operateTime, long timeSpent, HttpServletRequest request) {
        Optional<AetherException> optional = Optional.ofNullable(error);
        return Builder.builder(SysOperateRecordHolder::new)
                // 1 代表失败 0 代表成功
                .with(SysOperateRecordHolder::setOperateResult, optional.isPresent() ? OPERATE_RESULT_FAILED : OPERATE_RESULT_SUCCESS)
                .with(SysOperateRecordHolder::setOperateCode, optional.isPresent() ? optional.get().getCode() : CommonHttpStatus.SUCCESS.getCode())
                .with(SysOperateRecordHolder::setErrorReason, optional.map(AetherException::getMessage).orElse("-"))
                .with(SysOperateRecordHolder::setOperateIp, ServletUtil.getClientIP(request))
                .with(SysOperateRecordHolder::setOperateTime, operateTime)
                .with(SysOperateRecordHolder::setTimeSpent, timeSpent)
                .enhanceWith(SysOperateRecordHolder::setOperateUri, request::getRequestURI)
                .enhanceWith(SysOperateRecordHolder::setOperateMethod, request::getMethod)
                .build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysOperateRecordHolder.class.getSimpleName() + "[", "]")
                .add("operateId=" + operateId)
                .add("operateAccount='" + operateAccount + "'")
                .add("operateResult=" + operateResult)
                .add("operateCode=" + operateCode)
                .add("errorReason='" + errorReason + "'")
                .add("operateIp='" + operateIp + "'")
                .add("operateTime=" + operateTime)
                .add("timeSpent=" + timeSpent)
                .add("operateUri='" + operateUri + "'")
                .add("operateMethod=" + operateMethod)
                .add("id=" + id)
                .toString();
    }
}