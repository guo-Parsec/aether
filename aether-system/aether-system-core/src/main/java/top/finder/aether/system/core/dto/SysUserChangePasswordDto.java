package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

/**
 * <p>用户修改密码参数</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Setter
@Getter
@ApiModel(value = "用户修改密码参数")
public class SysUserChangePasswordDto implements IModel {
    private static final long serialVersionUID = 7945162360504785396L;

    @ApiModelProperty(value = "用户账户")
    @NotEmpty(message = "用户账户不可为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "登陆账户只能包含数字和英文")
    @Length(min = 4, max = 24, message = "登陆账户长度不能少于4,也不能超过24")
    private String account;

    @NotEmpty(message = "登陆密码不能为空")
    @Length(min = 6, max = 16, message = "登陆密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "密码只能包含数字、英文和[_@!.,]内的字符")
    private String password;

    @NotEmpty(message = "二次密码不能为空")
    @Length(min = 6, max = 16, message = "二次密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "二次密码只能包含数字、英文和[_@!.,]内的字符")
    private String checkPassword;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysUserChangePasswordDto.class.getSimpleName() + "[", "]")
                .add("account='" + account + "'")
                .add("password='" + password + "'")
                .add("checkPassword='" + checkPassword + "'")
                .toString();
    }
}
