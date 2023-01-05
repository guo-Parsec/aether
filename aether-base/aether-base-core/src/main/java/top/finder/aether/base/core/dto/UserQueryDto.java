package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * <p>用户查询参数</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Getter
@Setter
@ApiModel("用户查询参数")
public class UserQueryDto {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 登陆账户
     */
    @ApiModelProperty("登陆账户")
    protected String account;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    protected String nickname;

    /**
     * 用户性别集合
     */
    @ApiModelProperty("用户性别集合")
    protected Set<Integer> sexSet;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    protected String avatarUrl;

    /**
     * 出生日期起始
     */
    @ApiModelProperty("出生日期起始")
    protected LocalDate birthdayStarter;

    /**
     * 出生日期终止
     */
    @ApiModelProperty("出生日期终止")
    protected LocalDate birthdayEnd;

    /**
     * 用户类别集合
     */
    @ApiModelProperty("用户类别集合")
    protected Set<Integer> userTypeSet;
}
