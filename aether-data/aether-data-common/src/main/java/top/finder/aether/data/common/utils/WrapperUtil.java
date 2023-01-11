package top.finder.aether.data.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * <p>MybatisPlus Wrapper工具类</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public class WrapperUtil {
    public static <T> LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<T>();
    }
}
