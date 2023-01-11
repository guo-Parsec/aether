package top.finder.aether.base.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.base.api.model.DictModel;

import java.util.List;

/**
 * <p>数据字典查询接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Repository
public interface DictRepository {
    /**
     * <p>根据字典类型查询字典列表</p>
     *
     * @param dictTypeCode 字典类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 11:21
     */
    List<DictModel> findDictByType(@Param("dictTypeCode") String dictTypeCode);
}
