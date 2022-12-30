package top.finder.aether.data.core.config;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.EnvHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.support.pool.SecurityConstantPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * <p>远程调用拦截器</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        String currentAppName = EnvHelper.get(CommonConstantPool.APP_NAME_KEY);
        log.debug("应用[{}]正在进行远程调用", currentAppName);
        requestTemplate.header(CommonConstantPool.FEIGN_SOURCE_APP_HEAD_KEY, currentAppName);
        String header = CodeHelper.getHeadersToShare().get(SecurityConstantPool.TOKEN_IN_HEAD_KEY.toLowerCase(Locale.ROOT));
        if (StrUtil.isNotBlank(header)) {
            log.debug("远程服务注入安全认证请求头[{}]", header);
            requestTemplate.header(SecurityConstantPool.TOKEN_IN_HEAD_KEY, header);
        }
    }
}
