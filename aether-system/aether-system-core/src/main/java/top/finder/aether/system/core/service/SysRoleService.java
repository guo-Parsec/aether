package top.finder.aether.system.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.system.core.dto.*;
import top.finder.aether.system.core.entity.SysRole;
import top.finder.aether.system.core.vo.SysRoleVo;

import java.util.List;
import java.util.Set;

/**
 * <p>系统角色服务类接口</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * <p>新增：角色信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 9:25
     */
    void create(SysRoleCreateDto createDto);

    /**
     * <p>批量删除：角色信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/27 10:48
     */
    void delete(Set<Long> idSet);

    /**
     * <p>更新：角色信息</p>
     *
     * @param updateDto 更新入参
     * @author guocq
     * @date 2022/12/27 10:39
     */
    void update(SysRoleUpdateDto updateDto);

    /**
     * <p>查询：角色信息列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    List<SysRoleVo> listQuery(SysRoleQueryDto dto);

    /**
     * <p>分页查询角色信息</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/13 15:35
     */
    IPage<SysRoleVo> pageQuery(SysRolePageQueryDto dto);

    /**
     * <p>为角色赋予资源</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:14
     */
    void grantResourceToRole(GrantResourceToRoleDto dto);
}
