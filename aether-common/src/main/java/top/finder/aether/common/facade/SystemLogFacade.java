package top.finder.aether.common.facade;

import top.finder.aether.common.model.SystemLogInfo;

/**
 * <p>系统日志接口</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
public interface SystemLogFacade {
    /**
     * <p>保存操作日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2022/12/30 10:31
     */
    void saveOperateLog(SystemLogInfo systemLogInfo);

    /**
     * <p>保存登录日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2023/1/4 10:54
     */
    void saveLoginLog(SystemLogInfo systemLogInfo);
}
