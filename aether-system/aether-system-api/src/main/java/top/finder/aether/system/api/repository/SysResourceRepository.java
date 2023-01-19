package top.finder.aether.system.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.system.api.vo.SysResourceVo;

import java.util.List;
import java.util.Set;

/**
 * <p>系统资源持久化接口</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Repository
public interface SysResourceRepository {
    /**
     * <p>根据角色id集合查询资源url集合</p>
     *
     * @param roleIdSet 角色id集合
     * @return {@link List} 资源url集合
     * @author guocq
     * @date 2023/1/19 14:08
     */
    List<SysResourceVo> findResourceUrlByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);
}
