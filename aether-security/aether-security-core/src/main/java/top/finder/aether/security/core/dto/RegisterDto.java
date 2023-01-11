package top.finder.aether.security.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.base.api.dto.UserCreateDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static top.finder.aether.base.api.support.pool.BaseConstantPool.SEX_TYPE_UNKNOWN;

/**
 * <p>注册参数</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Getter
@Setter
@ApiModel("注册参数")
public class RegisterDto {
    /**
     * 登陆账户
     */
    @ApiModelProperty("登陆账户")
    @NotEmpty(message = "登陆账户不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "登陆账户只能包含数字和英文")
    @Length(min = 4, max = 24, message = "登陆账户长度不能少于4,也不能超过24")
    private String account;

    /**
     * 登陆密码
     */
    @ApiModelProperty("登陆密码")
    @NotEmpty(message = "登陆密码不能为空")
    @Length(min = 6, max = 16, message = "登陆密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "密码只能包含数字、英文和[_@!.,]内的字符")
    private String password;

    /**
     * 二次密码
     */
    @ApiModelProperty("二次密码")
    @NotEmpty(message = "二次密码不能为空")
    @Length(min = 6, max = 16, message = "二次密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "二次密码只能包含数字、英文和[_@!.,]内的字符")
    private String checkPassword;

    /**
    * <p>用户创建</p>
    * @return {@link UserCreateDto}
    * @author guocq
    * @date 2023/1/6 11:44
    */
    public UserCreateDto toUserCreateDto() {
        UserCreateDto dto = new UserCreateDto();
        dto.setAccount(account);
        dto.setPassword(password);
        dto.setCheckPassword(checkPassword);
        dto.setRegister(true);
        dto.setSex(SEX_TYPE_UNKNOWN);
        return dto;
    }
}
