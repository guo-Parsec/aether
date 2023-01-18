package top.finder.aether.system.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.system.core.dto.SysOperateRecordPageQueryDto;
import top.finder.aether.system.core.dto.SysOperateRecordQueryDto;
import top.finder.aether.system.core.vo.SysOperateRecordVo;

import java.util.List;

/**
 * <p>系统操作记录服务接口</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public interface SysOperateRecordService {
    /**
     * <p>查询：系统操作记录列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2023/01/18 16:07
     */
    List<SysOperateRecordVo> listQuery(SysOperateRecordQueryDto dto);

    /**
     * <p>分页查询：系统操作记录</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/01/18 16:07
     */
    IPage<SysOperateRecordVo> pageQuery(SysOperateRecordPageQueryDto dto);
}
