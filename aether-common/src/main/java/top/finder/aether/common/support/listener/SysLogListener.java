package top.finder.aether.common.support.listener;

import org.springframework.stereotype.Component;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.common.support.interfaces.ILogInterface;

import javax.annotation.Resource;

/**
 * <p>系统日志监听</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Component
public class SysLogListener {
    @Resource
    private ILogInterface logInterface;

    /**
     * <p>保存操作日志信息</p>
     *
     * @param logModel 日志模型
     * @author guocq
     * @date 2022/12/30 16:34
     */
    public void saveOperateLog(LogModel logModel) {
        logInterface.saveOperateLog(logModel);
    }

    /**
     * <p>保存登录日志信息</p>
     *
     * @param logModel 日志模型
     * @return void
     * @author guocq
     * @date 2023/1/4 10:55
     */
    public void saveLoginLog(LogModel logModel) {
        logInterface.saveLoginLog(logModel);
    }

}
