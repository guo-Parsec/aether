package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import java.time.LocalDate;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>用户查询参数</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Getter
@Setter
@ApiModel("用户查询参数")
public class SysUserQueryDto implements IModel {
    private static final long serialVersionUID = -5881965998574922617L;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", SysUserQueryDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("account='" + account + "'")
                .add("nickname='" + nickname + "'")
                .add("sexSet=" + sexSet)
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthdayStarter=" + birthdayStarter)
                .add("birthdayEnd=" + birthdayEnd)
                .add("userTypeSet=" + userTypeSet)
                .toString();
    }
}
