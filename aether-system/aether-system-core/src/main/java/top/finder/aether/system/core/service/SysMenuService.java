package top.finder.aether.system.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.system.core.dto.SysMenuCreateDto;
import top.finder.aether.system.core.dto.SysMenuPageQueryDto;
import top.finder.aether.system.core.dto.SysMenuQueryDto;
import top.finder.aether.system.core.dto.SysMenuUpdateDto;
import top.finder.aether.system.core.vo.SysMenuVo;

import java.util.List;
import java.util.Set;
/**
 * <p>系统菜单服务接口</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public interface SysMenuService {
    /**
     * <p>新增：系统菜单</p>
     *
     * @param dto 新增入参
     * @author guocq
     * @date 2023/01/18 15:50
     */
    void create(SysMenuCreateDto dto);

    /**
     * <p>批量删除：系统菜单</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/01/18 15:50
     */
    void delete(Set<Long> idSet);

    /**
     * <p>更新：系统菜单</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/01/18 15:50
     */
    void update(SysMenuUpdateDto dto);

    /**
     * <p>查询：系统菜单列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2023/01/18 15:50
     */
    List<SysMenuVo> listQuery(SysMenuQueryDto dto);

    /**
     * <p>分页查询：系统菜单</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/01/18 15:50
     */
    IPage<SysMenuVo> pageQuery(SysMenuPageQueryDto dto);
}
