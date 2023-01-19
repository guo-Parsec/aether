package top.finder.aether.system.api.facade;

import java.util.Map;
import java.util.Set;

/**
 * <p>系统用户Facade接口</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
public interface SysUserFacade {
    /**
     * <p>根据用户id查询用户相关安全详情</p>
     *
     * @param userId 用户id
     * @return java.util.Map<java.lang.String, java.util.Set < java.lang.String>>
     * @author guocq
     * @date 2023/1/19 14:16
     */
    Map<String, Set<String>> findSecurityDetailsByUserId(Long userId);

    /**
     * <p>清楚指定缓存</p>
     *
     * @param userId 用户id
     * @author guocq
     * @date 2023/1/19 15:17
     */
    void clearCache(Long userId);

    /**
     * <p>清除缓存</p>
     *
     * @author guocq
     * @date 2023/1/19 15:17
     */
    default void clearCache() {
        clearCache(null);
    }
}
