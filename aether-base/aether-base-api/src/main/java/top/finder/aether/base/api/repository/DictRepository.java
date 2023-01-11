package top.finder.aether.base.api.repository;

import org.springframework.stereotype.Repository;
import top.finder.aether.base.api.entity.Dict;
import top.finder.aether.data.common.mapper.CommonMapper;

/**
 * <p>数据字典查询接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Repository
public interface DictRepository extends CommonMapper<Dict> {
}
