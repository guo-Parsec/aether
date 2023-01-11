package top.finder.aether.security.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.common.support.pool.SecurityConstantPool;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>安全签名类</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Setter
@Getter
@ApiModel(value = "安全签名类")
public class SecuritySignature implements Serializable {
    private static final long serialVersionUID = -6325090388760295196L;

    @ApiModelProperty(value = "签名对象")
    private UserVo details;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户账户信息")
    private String account;

    @ApiModelProperty(value = "用户所拥有的角色码")
    private Set<String> roles;

    @ApiModelProperty(value = "用户所拥有的权限码")
    private Set<String> permissions;

    @ApiModelProperty(value = "用户所拥有的请求路径")
    private Set<String> urls;

    @ApiModelProperty(value = "令牌信息")
    private Token token;

    public SecuritySignature(UserVo details) {
        this.id = Long.valueOf(details.getId());
        this.account = details.getAccount();
        this.details = details;
        this.token = Token.ofName(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
    }

    /**
     * <p>注入令牌id信息</p>
     *
     * @param tokenId 令牌id
     * @author guocq
     * @date 2022/12/28 10:03
     */
    public void setTokenId(String tokenId) {
        this.setToken(Optional.ofNullable(this.getToken()).map(token -> {
            token.setId(tokenId);
            token.setContent(SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX + tokenId);
            return token;
        }).orElse(Token.ofId(tokenId)));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecuritySignature.class.getSimpleName() + "[", "]")
                .add("details=" + details)
                .add("id=" + id)
                .add("account='" + account + "'")
                .add("roles=" + roles)
                .add("permissions=" + permissions)
                .add("urls=" + urls)
                .add("token=" + token)
                .toString();
    }
}
