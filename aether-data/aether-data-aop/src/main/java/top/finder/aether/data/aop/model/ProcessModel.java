package top.finder.aether.data.aop.model;

import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.lang.reflect.Method;

/**
 * <p>切面结果模型</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface ProcessModel extends IModel {
    String VOID_METHOD_RETURN_TYPE = CommonConstantPool.VOID_METHOD_RETURN_TYPE;

    /**
     * <p>获取是否继续后置处理</p>
     *
     * @return boolean
     * @author guocq
     * @date 2022/12/14 18:35
     */
    boolean isProcessed();

    /**
     * <p>获取处理的方法对象</p>
     *
     * @return java.lang.reflect.Method
     * @author guocq
     * @date 2022/12/14 18:42
     */
    Method getMethod();
}
