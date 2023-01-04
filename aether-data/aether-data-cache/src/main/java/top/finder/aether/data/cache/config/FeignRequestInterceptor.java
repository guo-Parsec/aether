package top.finder.aether.data.cache.config;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.EnvHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.data.cache.support.runner.SystemSetting;

import java.util.Map;
import java.util.Optional;

import static top.finder.aether.common.support.pool.SecurityConstantPool.TOKEN_IN_HEAD_KEY;
import static top.finder.aether.data.cache.support.pool.SystemSettingConstantPool.FEIGN_SECRET;

/**
 * <p>远程调用拦截器</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    private final SystemSetting systemSetting;

    public FeignRequestInterceptor(SystemSetting systemSetting) {
        this.systemSetting = systemSetting;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            String currentAppName = EnvHelper.get(CommonConstantPool.APP_NAME_KEY);
            log.debug("应用[{}]正在进行远程调用", currentAppName);
            requestTemplate.header(CommonConstantPool.FEIGN_SOURCE_APP_HEAD_KEY, currentAppName);
            requestTemplate.header(CommonConstantPool.IS_FROM_GATEWAY, Boolean.TRUE.toString());
            Map<String, String> headersToShare = CodeHelper.getHeadersToShare();
            String header = null;
            Optional<String> optionalKey = headersToShare.keySet().stream().filter(key -> key.equalsIgnoreCase(TOKEN_IN_HEAD_KEY)).findFirst();
            if (optionalKey.isPresent()) {
                header = headersToShare.get(optionalKey.get());
            }
            if (StrUtil.isNotBlank(header)) {
                log.debug("远程服务注入安全认证请求头[{}]", header);
                requestTemplate.header(TOKEN_IN_HEAD_KEY, header);
            }
            String feignSecret = StrUtil.toStringOrNull(systemSetting.getAppSetting(currentAppName, FEIGN_SECRET));
            requestTemplate.header(FEIGN_SECRET, feignSecret);
        } finally {
            CodeHelper.clearHeadersToShare();
        }
    }
}
