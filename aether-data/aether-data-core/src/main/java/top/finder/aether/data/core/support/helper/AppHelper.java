package top.finder.aether.data.core.support.helper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.data.core.support.runner.SystemSetting;

import javax.servlet.http.HttpServletRequest;

import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.FEIGN_SECRET;

/**
 * <p>app辅助类</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
public class AppHelper {
    private static final Logger log = LoggerFactory.getLogger(AppHelper.class);

    /**
     * <p>获取当前请求来源app</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/5 15:34
     */
    public static String findCurrentRequestSourceApp() {
        return findCurrentRequestSourceApp(null);
    }

    /**
     * <p>获取当前请求来源app</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/5 15:34
     */
    public static String findCurrentRequestSourceApp(HttpServletRequest request) {
        request = request == null ? CodeHelper.getHttpServletRequest() : request;
        String requestPath = request.getRequestURI();
        String sourceApp = request.getHeader(CommonConstantPool.FEIGN_SOURCE_APP_HEAD_KEY);
        if (StrUtil.isBlank(sourceApp)) {
            log.debug("请求头中不存在[source-app]，代表属于当前服务");
            return SpringUtil.getApplicationName();
        }
        log.debug("从请求[{}]中获取来源app为[{}]", requestPath, sourceApp);
        return sourceApp;
    }

    /**
     * <p>是否为服务间调用</p>
     *
     * @return java.lang.Boolean
     * @author guocq
     * @date 2023/1/5 16:43
     */
    public static Boolean isFeignRequest(HttpServletRequest request) {
        request = request == null ? CodeHelper.getHttpServletRequest() : request;
        String sourceApp = findCurrentRequestSourceApp(request);
        String headerFeignSecret = request.getHeader(FEIGN_SECRET);
        if (StrUtil.isBlank(headerFeignSecret)) {
            log.warn("请求密钥为空，验证失败");
            return false;
        }
        SystemSetting bean = SpringUtil.getBean(SystemSetting.class);
        String feignSecret = StrUtil.toStringOrNull(bean.getAppSetting(sourceApp, FEIGN_SECRET));
        if (!headerFeignSecret.equals(feignSecret)) {
            log.error("请求密钥验证失败");
            return false;
        }
        return true;
    }
}
