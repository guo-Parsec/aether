package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.system.core.entity.SysOperateRecord;
import top.finder.aether.system.core.vo.SysOperateRecordVo;

/**
 * <p>系统操作记录数据转换类</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public class SysOperateRecordConverter {
    /**
     * <p>{@link SysOperateRecord}转换为{@link SysOperateRecordVo}转换器</p>
     */
    public static Converter<SysOperateRecord, SysOperateRecordVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysOperateRecordVo sysOperateRecordVo = new SysOperateRecordVo();
        sysOperateRecordVo.setId(source.getId());
        sysOperateRecordVo.setOperateId(source.getOperateId());
        sysOperateRecordVo.setOperateAccount(source.getOperateAccount());
        sysOperateRecordVo.setOperateResult(source.getOperateResult());
        sysOperateRecordVo.setOperateCode(source.getOperateCode());
        sysOperateRecordVo.setErrorReason(source.getErrorReason());
        sysOperateRecordVo.setOperateIp(source.getOperateIp());
        sysOperateRecordVo.setOperateTime(source.getOperateTime());
        sysOperateRecordVo.setTimeSpent(source.getTimeSpent());
        sysOperateRecordVo.setOperateUri(source.getOperateUri());
        sysOperateRecordVo.setOperateMethod(source.getOperateMethod());
        return sysOperateRecordVo;
    };

    /**
     * <p>{@link SysOperateRecord}转换为{@link SysOperateRecordVo}</p>
     *
     * @param entity {@link SysOperateRecord} 入参
     * @return {@link SysOperateRecordVo}
     * @author guocq
     * @date 2023/01/18 16:03
     */
    public static SysOperateRecordVo entityToVo(SysOperateRecord entity) {
        return entityToVoConverter.convert(entity);
    }
}
