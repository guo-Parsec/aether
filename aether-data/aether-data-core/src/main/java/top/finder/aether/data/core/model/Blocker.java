package top.finder.aether.data.core.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.annotation.BlockBean;
import top.finder.aether.common.support.annotation.BlockMethod;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * <p>拦截器实例</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Setter
@Getter
@Slf4j
public class Blocker implements IModel {
    private static final long serialVersionUID = 7323352604179840300L;

    /**
     * 拦截器id
     */
    private String blockId;

    /**
     * 拦截器beanClass对象
     */
    private Class<?> beanClass;

    /**
     * 拦截器方法对象
     */
    private Method method;

    /**
     * 拦截器类名称
     */
    private String className;

    /**
     * 拦截器bean名称
     */
    private String beanName;

    /**
     * 拦截器方法名称
     */
    private String methodName;

    /**
     * bean对象
     */
    private Object bean;

    private Blocker(String blockId, Class<?> beanClass, Method method, String className, String beanName, String methodName, Object bean) {
        this.blockId = blockId;
        this.beanClass = beanClass;
        this.method = method;
        this.className = className;
        this.beanName = beanName;
        this.methodName = methodName;
        this.bean = bean;
    }

    /**
     * <p>拦截器构建</p>
     *
     * @param beanClass beanClass
     * @param method    method
     * @return {@link Blocker}
     * @author guocq
     * @date 2023/1/6 10:35
     */
    public static Blocker of(Class<?> beanClass, Method method) {
        if (beanClass == null || method == null) {
            log.error("beanClass和method不能为空");
            throw new IllegalStateException("beanClass和method不能为空");
        }
        BlockBean blockBean = AnnotationUtils.findAnnotation(beanClass, BlockBean.class);
        String beanName = null;
        if (blockBean == null || StrUtil.isBlank(blockBean.name())) {
            beanName = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(beanClass.getSimpleName());
        } else {
            beanName = blockBean.name();
        }
        log.info("beanName为{}", beanName);
        BlockMethod blockMethod = AnnotationUtils.findAnnotation(method, BlockMethod.class);
        String methodName = null;
        if (blockMethod == null || StrUtil.isBlank(blockMethod.name())) {
            methodName = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(method.getName());
        } else {
            methodName = blockMethod.name();
        }
        log.info("methodName为{}", methodName);
        final String blockId = StrUtil.join(CommonConstantPool.POINT, beanName, methodName);
        log.info("拦截器id为{}", blockId);
        Object bean = SpringUtil.getBean(beanClass);
        return new Blocker(blockId, beanClass, method, beanClass.getName(), beanName, methodName, bean);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Blocker.class.getSimpleName() + "[", "]")
                .add("blockId='" + blockId + "'")
                .add("className='" + className + "'")
                .add("beanName='" + beanName + "'")
                .add("methodName='" + methodName + "'")
                .toString();
    }
}
