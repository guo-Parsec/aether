package top.finder.aether.base.core.service;

import top.finder.aether.common.model.LogModel;

/**
 * <p>系统日志服务接口</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
public interface LogService {
    /**
     * <p>保存操作日志信息</p>
     *
     * @param logModel 日志信息
     * @author guocq
     * @date 2022/12/30 10:31
     */
    void saveOperateLog(LogModel logModel);
}
