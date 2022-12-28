package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.base.api.vo.RoleVo;
import top.finder.aether.base.core.dto.RoleCreateDto;
import top.finder.aether.base.core.dto.RoleUpdateDto;
import top.finder.aether.base.core.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>系统角色服务类接口</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface RoleService extends IService<Role> {
    /**
     * <p>新增：角色信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 9:25
     */
    void create(RoleCreateDto createDto);

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
    void update(RoleUpdateDto updateDto);

    /**
     * <p>查询：角色信息列表</p>
     *
     * @param role 查询入参
     * @return {@link List<RoleVo>}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    List<RoleVo> listQuery(Role role);
}
