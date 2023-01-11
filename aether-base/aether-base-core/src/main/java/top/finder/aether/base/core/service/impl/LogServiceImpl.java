package top.finder.aether.base.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.core.dto.HiLoginQueryDto;
import top.finder.aether.base.core.dto.HiOperateLogQueryDto;
import top.finder.aether.base.core.entity.HiLogin;
import top.finder.aether.base.core.entity.HiOperateLog;
import top.finder.aether.base.core.mapper.HiLoginMapper;
import top.finder.aether.base.core.mapper.HiOperateLogMapper;
import top.finder.aether.base.core.service.LogService;
import top.finder.aether.base.core.vo.HiLoginVo;
import top.finder.aether.base.core.vo.HiOperateLogVo;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityFacade;

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
    @Resource
    private HiLoginMapper hiLoginMapper;


    /**
     * <p>保存操作日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2022/12/30 10:31
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOperateLog(SystemLogInfo systemLogInfo) {
        log.debug("保存操作日志信息开始，入参={}", systemLogInfo);
        setLoginInfo(systemLogInfo);
        hiOperateLogMapper.insert(HiOperateLog.transformToHiOperateLog(systemLogInfo));
        log.debug("保存操作日志信息成功");
    }

    /**
     * <p>保存登录日志信息</p>
     *
     * @param systemLogInfo 日志信息
     * @author guocq
     * @date 2023/1/4 10:28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginLog(SystemLogInfo systemLogInfo) {
        log.debug("保存登录日志信息开始，入参={}", systemLogInfo);
        hiLoginMapper.insert(HiLogin.transformToHiLoginLog(systemLogInfo));
        log.debug("保存登录日志信息成功");
    }

    /**
     * <p>分页查询操作日志信息</p>
     *
     * @param queryDto 分页参数
     * @return {@link IPage < HiOperateLogVo >}
     * @date 2023/1/4 11:11
     */
    @Override
    public IPage<HiOperateLogVo> operateLogPageQuery(HiOperateLogQueryDto queryDto) {
        Page<HiOperateLog> rawPage = new Page<>(queryDto.getCurrentPage(), queryDto.getPageSize());
        Wrapper<HiOperateLog> wrapper = new LambdaQueryWrapper<HiOperateLog>()
                .like(StrUtil.isNotBlank(queryDto.getAccessApp()), HiOperateLog::getAccessApp, queryDto.getAccessApp())
                .like(StrUtil.isNotBlank(queryDto.getOperateUserAccount()), HiOperateLog::getOperateUserAccount, queryDto.getOperateUserAccount())
                .eq(StrUtil.isNotBlank(queryDto.getMethodType()), HiOperateLog::getMethodType, queryDto.getMethodType())
                .eq(StrUtil.isNotBlank(queryDto.getAccessResult()), HiOperateLog::getAccessResult, queryDto.getAccessResult())
                .orderByAsc(HiOperateLog::getGmtCreate)
                .orderByAsc(HiOperateLog::getTimeConsuming);
        rawPage = hiOperateLogMapper.selectPage(rawPage, wrapper);
        return rawPage.convert(record -> TransformerHelper.transformer(record, HiOperateLogVo.class));
    }

    /**
     * <p>分页查询登录日志信息</p>
     *
     * @param queryDto 分页参数
     * @return {@link Page< HiLoginVo >}
     * @date 2023/1/4 11:11
     */
    @Override
    public IPage<HiLoginVo> loginLogPageQuery(HiLoginQueryDto queryDto) {
        Page<HiLogin> rawPage = new Page<>(queryDto.getCurrentPage(), queryDto.getPageSize());
        Wrapper<HiLogin> wrapper = new LambdaQueryWrapper<HiLogin>()
                .like(StrUtil.isNotBlank(queryDto.getLoginAccount()), HiLogin::getLoginAccount, queryDto.getLoginAccount())
                .eq(StrUtil.isNotBlank(queryDto.getLoginResult()), HiLogin::getLoginResult, queryDto.getLoginResult())
                .orderByAsc(HiLogin::getGmtCreate)
                .orderByAsc(HiLogin::getTimeConsuming);
        rawPage = hiLoginMapper.selectPage(rawPage, wrapper);
        return rawPage.convert(record -> TransformerHelper.transformer(record, HiLoginVo.class));
    }

    private void setLoginInfo(SystemLogInfo systemLogInfo) {
        if (SecurityFacade.isLogin()) {
            SecuritySignature signature = SecurityFacade.findSecuritySignature();
            systemLogInfo.setUserId(signature.getId());
            systemLogInfo.setUserAccount(signature.getAccount());
        }
    }
}
