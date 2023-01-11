package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.base.core.dto.ParamCreateDto;
import top.finder.aether.base.core.dto.ParamPageQueryDto;
import top.finder.aether.base.core.dto.ParamQueryDto;
import top.finder.aether.base.core.dto.ParamUpdateDto;
import top.finder.aether.base.api.vo.ParamVo;

import java.util.List;
import java.util.Set;

/**
 * <p>参数服务类</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
public interface ParamService {
    /**
     * <p>根据字典查询参数列表</p>
     *
     * @param dto 参数
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 11:13
     */
    List<ParamVo> list(ParamQueryDto dto);

    /**
     * <p>根据字典查询参数分页列表</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/9 11:14
     */
    IPage<ParamVo> page(ParamPageQueryDto dto);

    /**
     * <p>系统参数新增</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void create(ParamCreateDto dto);

    /**
     * <p>系统参数更新</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void update(ParamUpdateDto dto);

    /**
     * <p>系统参数批量删除</p>
     *
     * @param idSet 主键列表
     * @author guocq
     * @date 2023/1/9 11:15
     */
    void delete(Set<Long> idSet);
}
