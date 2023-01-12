package top.finder.aether.base.api.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.finder.aether.base.api.holders.SysParamHolders;

import java.util.List;

/**
 * <p>系统参数持久化接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Repository
public interface SysParamRepository {
    /**
     * <p>根据参数类型查询系统参数</p>
     *
     * @param paramTypeCode 参数类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    List<SysParamHolders> findParamByType(@Param("paramTypeCode") String paramTypeCode);

    /**
     * <p>根据参数码查询系统参数</p>
     *
     * @param paramCode 参数码
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    SysParamHolders findParamByParamCode(@Param("paramCode") String paramCode);
}
