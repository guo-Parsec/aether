package top.finder.aether.common.support.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import top.finder.aether.common.support.helper.EnvHelper;

/**
 * <p>SpringApplication监听器，用于初始化EnvHelper</p>
 *
 * @author guocq
 * @see EnvHelper
 * @since 2022/12/30
 */
public class ApplicationRunListener implements SpringApplicationRunListener, Ordered {
    private static final Logger log = LoggerFactory.getLogger(ApplicationRunListener.class);

    public ApplicationRunListener(SpringApplication application, String[] args){

    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log.info("应用开始启动");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        EnvHelper.setEnvironment(environment);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
