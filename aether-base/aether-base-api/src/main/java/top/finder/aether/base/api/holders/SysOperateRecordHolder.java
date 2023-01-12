package top.finder.aether.base.api.holders;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import java.time.LocalDateTime;
import java.util.StringJoiner;

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