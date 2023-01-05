package top.finder.aether.data.core.support.helper;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.EnvHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;

import javax.servlet.http.HttpServletRequest;

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
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        String requestPath = request.getRequestURI();
        String sourceApp = request.getHeader(CommonConstantPool.FEIGN_SOURCE_APP_HEAD_KEY);
        if (StrUtil.isBlank(sourceApp)) {
            log.debug("请求头中不存在[source-app]，代表属于当前服务");
            return EnvHelper.get(CommonConstantPool.APP_NAME_KEY, StrUtil.EMPTY);
        }
        log.debug("从请求[{}]中获取来源app为[{}]", requestPath, sourceApp);
        return sourceApp;
    }
}
