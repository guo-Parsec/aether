package top.finder.aether.common.support.helper;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.common.support.pool.CommonConstantPool;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>日志辅助类</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
public class LogHelper {
    public static void buildLogModel(LogModel logModel) {
        String appName = SpringUtil.getApplicationName();
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        String clientIp = ServletUtil.getClientIP(request);
        logModel.setAccessApp(appName);
        logModel.setAccessUrl(request.getRequestURI());
        logModel.setIp(clientIp);
        logModel.setDeviceName(buildDeviceName(request));
        logModel.setMethodType(request.getMethod());
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
