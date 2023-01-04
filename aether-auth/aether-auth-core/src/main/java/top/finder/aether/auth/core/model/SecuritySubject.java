package top.finder.aether.auth.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.common.support.pool.SecurityConstantPool;
import top.finder.aether.data.security.core.ISecuritySubject;
import top.finder.aether.data.security.core.Token;

import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>安全认证主体</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Setter
@Getter
@ApiModel(value = "安全认证主体")
public class SecuritySubject implements ISecuritySubject<UserVo> {
    private static final long serialVersionUID = 7115538001764343009L;

    @ApiModelProperty(value = "签名对象")
    private UserVo signature;

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

    public SecuritySubject(UserVo subject) {
        this.id = Long.valueOf(subject.getId());
        this.account = subject.getAccount();
        this.signature = subject;
        this.token = Token.ofName(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecuritySubject.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("account='" + account + "'")
                .add("roles=" + roles)
                .add("permissions=" + permissions)
                .add("urls=" + urls)
                .add("token=" + token)
                .toString();
    }
}
