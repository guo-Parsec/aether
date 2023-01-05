package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.annotation.DictValid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * <p>系统用户更新参数</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Getter
@Setter
@ApiModel("系统用户更新参数")
public class UserUpdateDto implements IModel {
    private static final long serialVersionUID = 9204873447045004720L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 登陆账户
     */
    @ApiModelProperty("登陆账户")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "密码只能包含数字和英文")
    @Length(min = 4, max = 24, message = "登陆账户长度不能少于4,也不能超过24")
    private String account;

    /**
     * 登陆密码
     */
    @ApiModelProperty("登陆密码")
    @Length(min = 6, max = 16, message = "登陆密码长度不能少于6,也不能超过16")
    @Pattern(regexp = "^[A-Za-z0-9_@!.,]+$", message = "密码只能包含数字、英文和[_@!.,]内的字符")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别")
    @DictValid(type = "sex", emptyValid = false)
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
    @DictValid(type = "user_type", emptyValid = false)
    private Integer userType;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserUpdateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("account='" + account + "'")
                .add("password='" + password + "'")
                .add("nickname='" + nickname + "'")
                .add("sex=" + sex)
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthday=" + birthday)
                .add("userType=" + userType)
                .toString();
    }
}
