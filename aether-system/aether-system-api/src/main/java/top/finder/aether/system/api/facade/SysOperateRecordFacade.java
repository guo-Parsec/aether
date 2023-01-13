package top.finder.aether.system.api.facade;

import top.finder.aether.system.api.holders.SysOperateRecordHolder;

/**
 * <p>系统操作记录Facade</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
public interface SysOperateRecordFacade {
    /**
     * <p>保存操作记录</p>
     *
     * @param sysOperateRecordHolder 操作记录
     * @author guocq
     * @date 2023/1/13 11:13
     */
    void saveOperateRecord(SysOperateRecordHolder sysOperateRecordHolder);
}
