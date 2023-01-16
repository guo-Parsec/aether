package top.finder.aether.common.support.helper;

/**
 * <p>转换器</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@FunctionalInterface
public interface Converter<S, T> {
    /**
     * <p>将源类型{@link S}转换为目标类型{@link T}</p>
     *
     * @param source {@link S} 源对象
     * @return {@link T}
     * @author guocq
     * @date 2023/1/16 13:55
     */
    T convert(S source);
}
