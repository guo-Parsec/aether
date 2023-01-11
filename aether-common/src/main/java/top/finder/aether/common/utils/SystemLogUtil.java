package top.finder.aether.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>日志辅助类</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
public class SystemLogUtil {
    /**
     * <p>构建日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2023/1/11 14:48
     */
    public static void buildLogModel(SystemLogInfo systemLogInfo) {
        String appName = SpringUtil.getApplicationName();
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        String clientIp = ServletUtil.getClientIP(request);
        systemLogInfo.setAccessApp(appName);
        systemLogInfo.setAccessUrl(request.getRequestURI());
        systemLogInfo.setIp(clientIp);
        systemLogInfo.setDeviceName(buildDeviceName(request));
        systemLogInfo.setMethodType(request.getMethod());
    }

    /**
     * <p>构建设备名称</p>
     *
     * @param request 请求信息
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/30 11:10
     */
    private static String buildDeviceName(HttpServletRequest request) {
        String userAgentStr = ServletUtil.getHeader(request, CommonConstantPool.USER_AGENT_HEAD, CharsetUtil.CHARSET_UTF_8);
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        OS os = userAgent.getOs();
        Browser browser = userAgent.getBrowser();
        return os.getName() + "#" + browser.getName();
    }
}
