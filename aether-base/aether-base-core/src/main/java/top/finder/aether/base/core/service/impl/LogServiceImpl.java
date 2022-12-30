package top.finder.aether.base.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.core.entity.HiOperateLog;
import top.finder.aether.base.core.mapper.HiOperateLogMapper;
import top.finder.aether.base.core.service.LogService;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.data.security.core.ISecuritySubject;
import top.finder.aether.data.security.core.SecurityContext;

import javax.annotation.Resource;

/**
 * <p>系统日志服务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Service(value = "logService")
public class LogServiceImpl implements LogService {
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
    @Resource
    private HiOperateLogMapper hiOperateLogMapper;


    /**
     * <p>保存操作日志信息</p>
     *
     * @param logModel 日志信息
     * @author guocq
     * @date 2022/12/30 10:31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperateLog(LogModel logModel) {
        log.debug("保存操作日志信息开始，入参={}", logModel);
        if (SecurityContext.isLogin()) {
            ISecuritySubject<UserVo> subject = SecurityContext.findSecuritySubject();
            logModel.setUserId(subject.getId());
            logModel.setUserAccount(subject.getAccount());
        }
        hiOperateLogMapper.insert(HiOperateLog.transformToHiOperateLog(logModel));
        log.debug("保存操作日志信息成功");
    }
}
