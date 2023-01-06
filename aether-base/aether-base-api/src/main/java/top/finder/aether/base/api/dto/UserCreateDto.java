package top.finder.aether.base.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.annotation.DictValid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * <p>系统用户创建参数</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Getter
@Setter
@ApiModel("系统用户创建参数")
public class UserCreateDto implements IModel {
    private static final long serialVersionUID = -2033395819286119626L;

    /**
     * 登陆账户
     */
    @ApiModelProperty("登陆账户")
    @NotEmpty(message = "登陆账户不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "密码只能包含数字和英文")
    @Length(min = 4, max = 24, message = "登陆账户长度不能少于4,也不能超过24")
    private String account;

    /**
     * 登陆密码
     */
    @ApiModelProperty(value = "登陆密码", hidden = true)
    @Length(min = 6, max = 16, message = "登陆密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "密码只能包含数字、英文和[_@!.,]内的字符")
    private String password;

    /**
     * 二次密码
     */
    @ApiModelProperty(value = "二次密码", hidden = true)
    @Length(min = 6, max = 16, message = "二次密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "二次密码只能包含数字、英文和[_@!.,]内的字符")
    private String checkPassword;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别")
    @DictValid(type = "sex")
    private Integer sex;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatarUrl;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private LocalDate birthday;

    /**
     * 用户类别
     */
    @ApiModelProperty("用户类别")
    @DictValid(type = "user_type")
    private Integer userType;

    @ApiModelProperty(value = "是否为注册", hidden = true)
    private boolean register = false;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCreateDto.class.getSimpleName() + "[", "]")
                .add("account='" + account + "'")
                .add("password='" + password + "'")
                .add("checkPassword='" + checkPassword + "'")
                .add("nickname='" + nickname + "'")
                .add("sex=" + sex)
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthday=" + birthday)
                .add("userType=" + userType)
                .toString();
    }
}
