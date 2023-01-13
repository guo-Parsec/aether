package top.finder.aether.system.core.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.system.core.entity.SysResource;
import top.finder.aether.data.common.mapper.CommonMapper;

import java.util.Set;

/**
 * <p>系统资源Mapper</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Repository
public interface SysResourceMapper extends CommonMapper<SysResource> {
    /**
     * <p>根据资源code删除资源数据</p>
     *
     * @param resourceCodeSet 资源code
     * @author guocq
     * @date 2023/1/10 13:33
     */
    void deleteWithResourceCode(@Param("resourceCodeSet") Set<String> resourceCodeSet);
}