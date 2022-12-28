package top.finder.aether.base.api.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;

/**
 * <p>用户对外服务接口</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@FeignClient(name = AppConstantPool.APP_BASE + BaseApiConstantPool.USER_WEB_API_PREFIX, contextId = "userClient")
public interface UserClient {
    /**
     * <p>加载用户信息并验证是否匹配</p>
     *
     * @param account  账户信息
     * @param password 传入密码
     * @return {@link Apis<UserVo>}
     * @author guocq
     * @date 2022/12/28 15:23
     */
    @ApiOperation(value = "加载用户", notes = "加载用户信息并验证是否匹配")
    @GetMapping(value = "/load")
    Apis<UserVo> loadUser(@RequestParam("account") String account, @RequestParam("password") String password);
}
