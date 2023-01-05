package top.finder.aether.common.support.function;

/**
 * <p>三元消费函数接口</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@FunctionalInterface
public interface TernaryConsumer<T, U, R> {
    /**
     * <p>对给定的参数执行该操作。</p>
     *
     * @param t 第一个输入参数
     * @param u 第二个输入参数
     * @param r 第三个输入参数
     * @author guocq
     * @date 2023/1/5 10:28
     */
    void accept(T t, U u, R r);
}
