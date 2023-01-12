package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.base.core.dto.SysParamCreateDto;
import top.finder.aether.base.core.dto.SysSysParamPageQueryDto;
import top.finder.aether.base.core.dto.SysParamQueryDto;
import top.finder.aether.base.core.dto.SysParamUpdateDto;
import top.finder.aether.base.core.vo.SysParamVo;

import java.util.List;
import java.util.Set;

/**
 * <p>参数服务类</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
public interface SysParamService {
    /**
     * <p>根据字典查询参数列表</p>
     *
     * @param dto 参数
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 11:13
     */
    List<SysParamVo> list(SysParamQueryDto dto);

    /**
     * <p>根据字典查询参数分页列表</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/9 11:14
     */
    IPage<SysParamVo> page(SysSysParamPageQueryDto dto);

    /**
     * <p>系统参数新增</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void create(SysParamCreateDto dto);

    /**
     * <p>系统参数更新</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void update(SysParamUpdateDto dto);

    /**
     * <p>系统参数批量删除</p>
     *
     * @param idSet 主键列表
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void delete(Set<Long> idSet);
}
