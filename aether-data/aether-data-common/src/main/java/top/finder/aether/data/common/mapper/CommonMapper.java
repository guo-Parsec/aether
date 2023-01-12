package top.finder.aether.data.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.finder.aether.data.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>通用mapper</p>
 *
 * @author guocq
 * @since 2023/1/3
 */
public interface CommonMapper<T extends BaseEntity> extends BaseMapper<T> {
    /**
     * <p>根据主键逻辑删除数据</p>
     *
     * @param id 主键
     * @return int
     * @author guocq
     * @date 2023/1/3 13:34
     */
    int logicDeleteById(Serializable id);

    /**
     * <p>根据id集合逻辑删除数据</p>
     *
     * @param idList  id集合
     * @param nowTime 当前时间戳
     * @author guocq
     * @date 2023/1/3 13:40
     */
    void logicBatchDeleteByIds(@Param(Constants.COLL) Collection<?> idList,
                               @Param("nowTime") long nowTime);

    /**
     * <p>批量新增</p>
     *
     * @param entityList 实体列表
     * @author guocq
     * @date 2023/1/12 13:55
     */
    void batchInsert(@Param(Constants.LIST) List<T> entityList);
}
