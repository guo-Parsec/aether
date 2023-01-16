package top.finder.aether.system.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.system.core.dto.SysResourceCreateDto;
import top.finder.aether.system.core.dto.SysResourcePageQueryDto;
import top.finder.aether.system.core.dto.SysResourceQueryDto;
import top.finder.aether.system.core.dto.SysResourceUpdateDto;
import top.finder.aether.system.core.vo.SysResourceVo;

import java.util.List;
import java.util.Set;

/**
 * <p>系统资源业务接口</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
public interface SysResourceService {
    /**
     * <p>创建系统资源信息</p>
     *
     * @param dto 新增参数
     * @author guocq
     * @date 2023/1/16 15:20
     */
    void create(SysResourceCreateDto dto);

    /**
     * <p>更新系统资源信息</p>
     *
     * @param dto 新增参数
     * @author guocq
     * @date 2023/1/16 15:20
     */
    void update(SysResourceUpdateDto dto);

    /**
     * <p>批量删除：资源信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/1/16 15:21
     */
    void delete(Set<Long> idSet);

    /**
     * <p>查询：系统资源信息列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    List<SysResourceVo> listQuery(SysResourceQueryDto dto);

    /**
     * <p>分页查询系统资源信息</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/13 15:35
     */
    IPage<SysResourceVo> pageQuery(SysResourcePageQueryDto dto);
}
