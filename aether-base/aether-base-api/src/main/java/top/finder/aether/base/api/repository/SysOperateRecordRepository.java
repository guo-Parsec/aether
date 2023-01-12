package top.finder.aether.base.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.base.api.holders.SysOperateRecordHolder;

/**
 * <p>系统操作记录持久层</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Repository
public interface SysOperateRecordRepository {
    /**
     * <p>保存操作记录</p>
     *
     * @param record 操作记录
     * @author guocq
     * @date 2023/1/12 16:28
     */
    void saveOperateRecord(@Param("record") SysOperateRecordHolder record);
}
