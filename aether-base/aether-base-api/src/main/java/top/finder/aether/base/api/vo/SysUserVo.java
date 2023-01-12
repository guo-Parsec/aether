package top.finder.aether.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.support.annotation.DictTranslate;
import top.finder.aether.data.common.vo.BaseDataVo;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * <p>用户展示数据</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@Setter
@Getter
@ApiModel("用户展示数据")
public class SysUserVo extends BaseDataVo {
    private static final long serialVersionUID = -6142823498820336560L;

    public static final SysUserVo EMPTY = new SysUserVo();

    /**
     * 登陆账户
     */
    @ApiModelProperty("登陆账户")
    private String account;

    /**
     * 登陆密码
     */
    @ApiModelProperty(value = "登陆密码", hidden = true)
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
    private Integer sex;

    /**
     * 性别值转义
     */
    @ApiModelProperty("性别值转义")
    @DictTranslate(value = "sex", type = "sex")
    private String sexName;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatarUrl;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    /**
     * 用户类别
     */
    @ApiModelProperty("用户类别")
    private Integer userType;

    /**
     * 用户类别转义
     */
    @ApiModelProperty("用户类别转义")
    @DictTranslate(value = "userType", type = "user_type")
    private String userTypeName;

    /**
     * 启用状态
     */
    @ApiModelProperty("启用状态")
    private Integer enableStatus;

    /**
     * 启用状态转义
     */
    @ApiModelProperty("启用状态转义")
    @DictTranslate(value = "enableStatus", type = "enable_status")
    private String enableStatusName;

    /**
     * 是否认证过
     */
    @ApiModelProperty("是否认证过")
    private Boolean certified;

    public static SysUserVo emptyUser(String account) {
        EMPTY.setCertified(false);
        EMPTY.setAccount(account);
        return EMPTY;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysUserVo.class.getSimpleName() + "[", "]")
                .add("account='" + account + "'")
                .add("password='" + password + "'")
                .add("nickname='" + nickname + "'")
                .add("sex=" + sex)
                .add("sexName='" + sexName + "'")
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthday=" + birthday)
                .add("userType=" + userType)
                .add("userTypeName='" + userTypeName + "'")
                .add("enableStatus=" + enableStatus)
                .add("enableStatusName='" + enableStatusName + "'")
                .add("certified=" + certified)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id='" + id + "'")
                .toString();
    }
}
