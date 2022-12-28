package top.finder.aether.common.support.enums;

/**
 * <p>基础数据枚举</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface BaseEnum<C, N> extends IEnum<C> {
    /**
     * <p>获取枚举名称值</p>
     *
     * @return N
     * @author guocq
     * @date 2022/12/27 9:34
     */
    N getMessage();

    /**
     * <p>获取枚举消息值 - 实际为获取枚举名称值</p>
     *
     * @return N
     * @author guocq
     * @date 2022/12/27 9:35
     */
    default N getName() {
        return getMessage();
    }
}
