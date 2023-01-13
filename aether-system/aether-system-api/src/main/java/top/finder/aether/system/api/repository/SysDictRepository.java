package top.finder.aether.system.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.system.api.holders.SysDictHolders;

import java.util.List;

/**
 * <p>数据字典持久化接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Repository
public interface SysDictRepository {
    /**
     * <p>根据字典类型查询字典列表</p>
     *
     * @param dictTypeCode 字典类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 11:21
     */
    List<SysDictHolders> findDictByType(@Param("dictTypeCode") String dictTypeCode);
}
