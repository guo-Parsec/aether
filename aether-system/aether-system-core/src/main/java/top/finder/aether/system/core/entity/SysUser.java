package top.finder.aether.system.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * <p>系统用户</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@Setter
@Getter
@TableName(value = "sys_user")
public class SysUser extends BaseDataEntity {
    private static final long serialVersionUID = 8261509168956698339L;

    /**
     * 登陆账户
     */
    private String account;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 用户类别
     */
    private Integer userType;

    /**
     * 启用状态
     */
    private Integer enableStatus;

    public SysUser() {
    }

    public SysUser(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysUser.class.getSimpleName() + "[", "]")
                .add("account='" + account + "'")
                .add("password='" + password + "'")
                .add("nickname='" + nickname + "'")
                .add("sex=" + sex)
                .add("avatarUrl='" + avatarUrl + "'")
                .add("birthday=" + birthday)
                .add("userType=" + userType)
                .add("enableStatus=" + enableStatus)
                .add("deleteAt='" + deleteAt + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}
