package top.finder.aether.system.core.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.system.core.entity.SysOperateRecord;

import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>系统操作记录数据查询入参</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Getter
@Setter
@ApiModel("系统操作记录数据查询入参")
public class SysOperateRecordQueryDto implements IModel {
    private static final long serialVersionUID = 589061390128873402L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    protected Long id;

    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    protected Long operateId;

    /**
     * 操作人账户
     */
    @ApiModelProperty(value = "操作人账户")
    protected String operateAccount;

    /**
     * 操作结果
     */
    @ApiModelProperty(value = "操作结果")
    protected Set<Integer> operateResultSet;

    /**
     * <p>获取查询通用wrapper</p>
     *
     * @return {@link LambdaQueryWrapper}
     * @author guocq
     * @date 2023/01/18 16:03
     */
    public LambdaQueryWrapper<SysOperateRecord> getCommonWrapper() {
        LambdaQueryWrapper<SysOperateRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(this.id), SysOperateRecord::getId, this.id);
        wrapper.eq(ObjectUtil.isNotNull(this.operateId), SysOperateRecord::getOperateId, this.operateId);
        wrapper.in(CollUtil.isNotEmpty(this.operateResultSet), SysOperateRecord::getOperateResult, this.operateResultSet);
        return wrapper;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysOperateRecordQueryDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("operateId='" + operateId + "'")
                .add("operateAccount='" + operateAccount + "'")
                .add("operateResultSet='" + operateResultSet + "'")
                .toString();
    }
}
