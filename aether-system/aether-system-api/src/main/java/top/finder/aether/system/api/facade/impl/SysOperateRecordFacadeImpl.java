package top.finder.aether.system.api.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.data.common.component.SnowflakeWorker;
import top.finder.aether.system.api.facade.SysOperateRecordFacade;
import top.finder.aether.system.api.holders.SysOperateRecordHolder;
import top.finder.aether.system.api.repository.SysOperateRecordRepository;

/**
 * <p>系统操作记录Facade实现</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Component(value = "sysOperateRecordFacade")
public class SysOperateRecordFacadeImpl implements SysOperateRecordFacade {
    private static final Logger log = LoggerFactory.getLogger(SysOperateRecordFacadeImpl.class);
    private final SysOperateRecordRepository repository;

    public SysOperateRecordFacadeImpl(SysOperateRecordRepository repository) {
        this.repository = repository;
    }

    /**
     * <p>保存操作记录</p>
     *
     * @param sysOperateRecordHolder 操作记录
     * @author guocq
     * @date 2023/1/13 11:13
     */
    @Override
    public void saveOperateRecord(SysOperateRecordHolder sysOperateRecordHolder) {
        log.debug("保存操作记录, 入参={}", sysOperateRecordHolder);
        sysOperateRecordHolder.setId(SnowflakeWorker.getBean().nextId());
        repository.saveOperateRecord(sysOperateRecordHolder);
    }
}