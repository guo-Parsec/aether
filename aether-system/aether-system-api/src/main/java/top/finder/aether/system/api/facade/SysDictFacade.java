package top.finder.aether.system.api.facade;

import top.finder.aether.system.api.holders.SysDictHolders;

import java.util.List;
import java.util.Optional;

/**
 * <p>字典Facade接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public interface SysDictFacade {
    /**
     * <p>根据字典类型查询字典列表</p>
     *
     * @param dictTypeCode 字典类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 9:20
     */
    List<SysDictHolders> findDictByType(String dictTypeCode);

    /**
     * <p>根据字典类型和字典码查询唯一字典</p>
     *
     * @param dictTypeCode 字典类型
     * @param dictCode     字典码
     * @return {@link Optional}
     * @author guocq
     * @date 2023/1/11 9:43
     */
    Optional<SysDictHolders> findDictByTypeAndCode(String dictTypeCode, Integer dictCode);
}
