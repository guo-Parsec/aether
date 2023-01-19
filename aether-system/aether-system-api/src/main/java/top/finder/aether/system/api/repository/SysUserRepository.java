package top.finder.aether.system.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.system.api.vo.SysUserVo;

/**
 * <p>系统用户持久层</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Repository
public interface SysUserRepository {
    /**
     * <p>根据id查询用户信息</p>
     *
     * @param id 主键
     * @return {@link SysUserVo}
     * @author guocq
     * @date 2023/1/19 14:47
     */
    SysUserVo findById(@Param("id") Long id);
}
