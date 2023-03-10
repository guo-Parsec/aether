package top.finder.aether.system.api.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import top.finder.aether.system.api.dto.SysUserCreateDto;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;
import top.finder.aether.data.core.entity.UserDetails;

/**
 * <p>用户对外服务接口</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@FeignClient(name = AppConstantPool.APP_SYSTEM + SystemApiConstantPool.USER_WEB_API_PREFIX, contextId = "userClient")
public interface SysUserClient {
    /**
     * <p>加载用户信息并验证是否匹配</p>
     *
     * @param account  账户信息
     * @param password 传入密码
     * @return {@link Apis}
     * @author guocq
     * @date 2022/12/28 15:23
     */
    @ApiOperation(value = "加载用户", notes = "加载用户信息并验证是否匹配")
    @GetMapping(value = "/load")
    Apis<UserDetails> loadUser(@RequestParam("account") String account, @RequestParam("password") String password);

    /**
     * <p>创建用户信息</p>
     *
     * @param dto 创建参数
     * @return {@link Apis}
     * @author guocq
     * @date 2023/1/5 16:17
     */
    @PostMapping(value = "create.do")
    Apis<Void> create(@RequestBody @Validated SysUserCreateDto dto);
}
