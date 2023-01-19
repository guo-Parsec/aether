package top.finder.aether.system.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.system.api.vo.SysRoleVo;

import java.util.List;

/**
 * <p>系统角色持久化接口</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Repository
public interface SysRoleRepository {
    /**
     * <p>根据用户id查询角色码值列表</p>
     *
     * @param userId 用户id
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 10:44
     */
    List<SysRoleVo> findRoleCodeByUserId(@Param("userId") Long userId);
}
