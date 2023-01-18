package top.finder.aether.system.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.finder.aether.data.core.support.helper.PageHelper;
import top.finder.aether.system.core.converter.SysOperateRecordConverter;
import top.finder.aether.system.core.dto.SysOperateRecordPageQueryDto;
import top.finder.aether.system.core.dto.SysOperateRecordQueryDto;
import top.finder.aether.system.core.entity.SysOperateRecord;
import top.finder.aether.system.core.mapper.SysOperateRecordMapper;
import top.finder.aether.system.core.service.SysOperateRecordService;
import top.finder.aether.system.core.vo.SysOperateRecordVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.record.SysOperateRecordCacheConstantPool.M_VO_RECORD;

/**
 * <p>系统操作记录服务接口实现类</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Service(value = "sysOperateRecordService")
public class SysOperateRecordServiceImpl implements SysOperateRecordService {
    private static final Logger log = LoggerFactory.getLogger(SysOperateRecordServiceImpl.class);

    @Resource
    private SysOperateRecordMapper mapper;

    /**
     * <p>查询：系统操作记录列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2023/01/18 16:07
     */
    @Override
    @Cacheable(cacheNames = M_VO_RECORD, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<SysOperateRecordVo> listQuery(SysOperateRecordQueryDto dto) {
        List<SysOperateRecord> sysOperateRecordList = mapper.selectList(dto.getCommonWrapper());
        return sysOperateRecordList.stream().map(SysOperateRecordConverter::entityToVo).collect(Collectors.toList());
    }

    /**
     * <p>分页查询：系统操作记录</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/01/18 16:07
     */
    @Override
    public IPage<SysOperateRecordVo> pageQuery(SysOperateRecordPageQueryDto dto) {
        IPage<SysOperateRecord> page = PageHelper.initPage(dto);
        page = mapper.selectPage(page, dto.getCommonWrapper());
        return page.convert(SysOperateRecordConverter::entityToVo);
    }
}
