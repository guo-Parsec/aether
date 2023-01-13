package top.finder.aether.system.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.system.core.dto.SysDictCreateDto;
import top.finder.aether.system.core.dto.SysDictUpdateDto;
import top.finder.aether.system.core.entity.SysDict;
import top.finder.aether.system.core.vo.SysDictVo;

import java.util.List;
import java.util.Set;

/**
 * <p>数据字典服务接口</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
public interface SysDictService extends IService<SysDict> {
    /**
     * <p>新增：字典信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    void create(SysDictCreateDto createDto);

    /**
     * <p>批量删除：角色信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/29 15:04
     */
    void delete(Set<Long> idSet);

    /**
     * <p>更新：字典信息</p>
     *
     * @param updateDto 更新入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    void update(SysDictUpdateDto updateDto);

    /**
     * <p>查询：字典信息列表</p>
     *
     * @param sysDict 查询入参
     * @return {@link List < DictVo >}
     * @author guocq
     * @date 2022/12/29 15:03
     */
    List<SysDictVo> listQuery(SysDict sysDict);
}
