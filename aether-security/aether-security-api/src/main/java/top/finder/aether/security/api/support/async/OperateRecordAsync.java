package top.finder.aether.security.api.support.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.security.api.SecurityContext;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityFacade;
import top.finder.aether.system.api.facade.SysOperateRecordFacade;
import top.finder.aether.system.api.holders.SysOperateRecordHolder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>操作记录异步服务</p>
 *
 * @author guocq
 * @since 2023/1/13
 */
@Component(value = "operateRecordAsync")
public class OperateRecordAsync {
    private static final Logger log = LoggerFactory.getLogger(OperateRecordAsync.class);

    private final SysOperateRecordFacade facade;

    public OperateRecordAsync(SysOperateRecordFacade facade) {
        this.facade = facade;
    }

    @Async("recordAsyncTaskExecutor")
    public void execSaveOperateRecord(AetherException error, LocalDateTime operateTime, Long timeSpent, String uri, String method, String ip) {
        SysOperateRecordHolder holder = SysOperateRecordHolder.buildRecordHolder(error, operateTime, timeSpent);
        holder.setOperateIp(ip);
        holder.setOperateUri(uri);
        holder.setOperateMethod(method);
        SecuritySignature securitySignature = SecurityContext.get();
        holder.setOperateId(Optional.ofNullable(securitySignature).map(SecuritySignature::getId).orElse(0L));
        holder.setOperateAccount(Optional.ofNullable(securitySignature).map(SecuritySignature::getAccount).orElse("anon"));
        facade.saveOperateRecord(holder);
    }

    private SecuritySignature findSecuritySignature(HttpServletRequest request) {
        try {
            SecuritySignature securitySignature = SecurityFacade.findSecuritySignature(request);
            if (securitySignature == null) {
                throw new AetherException("用户未登录");
            }
            return securitySignature;
        } catch (Exception e) {
            log.warn("记录请求{}时用户未登录", request.getRequestURI());
            return null;
        }
    }
}
