package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.finder.aether.base.core.vo.HiLoginVo;
import top.finder.aether.base.core.vo.HiOperateLogVo;
import top.finder.aether.base.core.dto.HiLoginQueryDto;
import top.finder.aether.base.core.dto.HiOperateLogQueryDto;
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

    /**
     * <p>保存登录日志信息</p>
     *
     * @param logModel 日志信息
     * @author guocq
     * @date 2023/1/4 10:28
     */
    void saveLoginLog(LogModel logModel);

    /**
     * <p>分页查询操作日志信息</p>
     *
     * @param queryDto 分页参数
     * @return {@link Page<HiOperateLogVo>}
     * @date 2023/1/4 11:11
     */
    IPage<HiOperateLogVo> operateLogPageQuery(HiOperateLogQueryDto queryDto);

    /**
     * <p>分页查询登录日志信息</p>
     *
     * @param queryDto 分页参数
     * @return {@link Page<HiLoginVo>}
     * @date 2023/1/4 11:11
     */
    IPage<HiLoginVo> loginLogPageQuery(HiLoginQueryDto queryDto);
}
