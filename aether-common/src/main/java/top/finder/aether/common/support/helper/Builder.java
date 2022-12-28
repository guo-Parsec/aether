package top.finder.aether.common.support.helper;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>建造者辅助类</p>
 *
 * @author guocq
 * @since 2022/11/28
 **/
public class Builder<T> {
    /**
     * 调用方指定类构造器
     */
    private final Supplier<T> constructor;

    /**
     * 指定类所有需要初始化的类属性
     */
    private final List<Consumer<T>> injects = Lists.newArrayList();

    @Getter
    private boolean with;

    Builder(Supplier<T> constructor) {
        this.constructor = constructor;
        this.with = true;
    }

    /**
     * <p>获取构造器</p>
     *
     * @param constructor 构造方法
     * @return {@link Builder<T>}
     * @author guocq
     * @date 2022/11/28 16:18
     */
    public static <T> Builder<T> builder(Supplier<T> constructor) {
        return new Builder<T>(constructor);
    }

    /**
     * <p>设置属性值</p>
     *
     * @param setter setter
     * @param value  值
     * @return {@link Builder<T>}
     * @author guocq
     * @date 2022/11/28 16:18
     */
    public <P> Builder<T> with(BiConsumer<T, P> setter, P value) {
        return with(true, setter, value);
    }

    /**
     * <p>满足条件{@code condition} 才能设置属性</p>
     *
     * @param condition 条件
     * @param setter    setter
     * @param value     值
     * @return {@link Builder<T>}
     * @author guocq
     * @date 2022/11/29 15:34
     */
    public <P> Builder<T> with(boolean condition, BiConsumer<T, P> setter, P value) {
        if (condition) {
            injects.add(instance -> setter.accept(instance, value));
        }
        this.with = true;
        return this;
    }

    /**
     * <p>设置属性值</p>
     *
     * @param setter setter
     * @param getter getter
     * @return {@link Builder<T>}
     * @author guocq
     * @date 2022/11/28 16:18
     */
    public <P> Builder<T> enhanceWith(BiConsumer<T, P> setter, Supplier<P> getter) {
        return enhanceWith(true, setter, getter);
    }

    /**
     * <p>满足条件{@code condition} 才能设置属性</p>
     *
     * @param condition 条件
     * @param setter    setter
     * @param getter    getter
     * @return {@link Builder<T>}
     * @author guocq
     * @date 2022/11/28 16:18
     */
    public <P> Builder<T> enhanceWith(boolean condition, BiConsumer<T, P> setter, Supplier<P> getter) {
        if (condition) {
            injects.add(instance -> setter.accept(instance, getter.get()));
        }
        this.with = true;
        return this;
    }

    /**
     * <p>最终对象构建</p>
     *
     * @return T
     * @author guocq
     * @date 2022/11/28 16:18
     */
    public T build() {
        T instance = constructor.get();
        injects.forEach(inject -> inject.accept(instance));
        this.with = false;
        return instance;
    }
}
