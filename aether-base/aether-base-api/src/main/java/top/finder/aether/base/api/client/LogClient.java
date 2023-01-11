package top.finder.aether.base.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.interfaces.ILogInterface;
import top.finder.aether.common.support.pool.AppConstantPool;

/**
 * <p>日志对外接口</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@FeignClient(name = AppConstantPool.APP_BASE + BaseApiConstantPool.LOG_WEB_API_PREFIX, contextId = "logClient")
public interface LogClient extends ILogInterface {
    /**
     * <p>保存操作日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @return {@link Apis<Void>}
     * @author guocq
     * @date 2022/12/30 11:52
     */
    @PostMapping(value = "/operate-log/save.do")
    Apis<Void> doSaveOperateLog(@RequestBody @Validated SystemLogInfo systemLogInfo);

    /**
     * <p>保存登录日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @return {@link Apis<Void>}
     * @author guocq
     * @date 2023/01/04 10:52
     */
    @PostMapping(value = "/login-log/save.do")
    Apis<Void> doSaveLoginLog(@RequestBody @Validated SystemLogInfo systemLogInfo);

    /**
     * <p>保存操作日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2022/12/30 10:31
     */
    @Override
    default void saveOperateLog(SystemLogInfo systemLogInfo) {
        this.doSaveOperateLog(systemLogInfo);
    }

    /**
     * <p>保存登录日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2023/1/4 10:54
     */
    @Override
    default void saveLoginLog(SystemLogInfo systemLogInfo) {
        this.doSaveLoginLog(systemLogInfo);
    }
}
