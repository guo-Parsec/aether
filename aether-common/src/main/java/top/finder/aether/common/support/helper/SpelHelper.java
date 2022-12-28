package top.finder.aether.common.support.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Maps;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>SPEL表达式辅助类</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public class SpelHelper {
    /**
     * <p>解析表达式字符串</p>
     *
     * @param express 表达式字符串
     * @param varMap  传递的参数
     * @param tClass  结果的类对象
     * @param appCtx  应用上下文
     * @return T
     * @author guocq
     * @date 2022/12/15 11:22
     */
    public static <T> T parse(String express, Map<String, Object> varMap, Class<T> tClass, ApplicationContext appCtx) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctx = ObjectUtil.isNull(appCtx) ? new StandardEvaluationContext() : new StandardEvaluationContext(appCtx);
        varMap.forEach(ctx::setVariable);
        return parser.parseExpression(express).getValue(ctx, tClass);
    }

    /**
     * <p>解析表达式字符串</p>
     *
     * @param express 表达式字符串
     * @param varMap  传递的参数
     * @param tClass  结果的类对象
     * @return T
     * @author guocq
     * @date 2022/12/15 11:22
     */
    public static <T> T parse(String express, Map<String, Object> varMap, Class<T> tClass) {
        return parse(express, varMap, tClass, null);
    }

    /**
     * <p>根据{@code method}构建该方法的{@code varMap},参数列表组成{参数名,参数值}，返回结果则组成{result,结果值}</p>
     *
     * @param method 方法
     * @param args   方法参数
     * @param result 方法结果
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author guocq
     * @date 2022/12/15 11:24
     */
    public static Map<String, Object> buildMethodVarMap(Method method, Object[] args, Object result, boolean isNeedInjectResult) {
        Assert.notNull(method, "method不能为null");
        Assert.notEmpty(args, "args不能为空");
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        if (ArrayUtil.isEmpty(parameterNames)) {
            return Maps.newHashMap();
        }
        Map<String, Object> varMap = Maps.newHashMapWithExpectedSize(parameterNames.length + 1);
        for (int i = 0; i < parameterNames.length; i++) {
            varMap.put(parameterNames[i], args[i]);
        }
        if (isNeedInjectResult) {
            varMap.put("result", result);
        }
        return varMap;
    }

    /**
     * <p>根据{@code method}构建该方法的{@code varMap},参数列表组成{参数名,参数值}，返回结果则组成{result,结果值}</p>
     *
     * @param method 方法
     * @param args   方法参数
     * @param result 方法结果
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author guocq
     * @date 2022/12/15 11:35
     */
    public static Map<String, Object> buildMethodVarMap(Method method, Object[] args, Object result) {
        boolean isVoidMethod = CommonConstantPool.VOID_METHOD_RETURN_TYPE.equals(method.getReturnType().getName());
        return buildMethodVarMap(method, args, result, true);
    }

}
