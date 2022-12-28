package top.finder.aether.data.core.support.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;

/**
 * <p>mysql注入方法抽象类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
public abstract class AbstractMySqlMethod extends AbstractMethod {
    private static final long serialVersionUID = 5384727239570983105L;
    protected String methodName;

    /**
     * @param methodName 方法名
     * @since 3.5.0
     */
    public AbstractMySqlMethod(String methodName) {
        super(methodName);
        this.methodName = methodName;
    }

    /**
     * <p>注入方法名称获取</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/13 11:43
     */
    public abstract String getMethodName();
}
