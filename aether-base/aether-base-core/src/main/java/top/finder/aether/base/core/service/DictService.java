package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.base.core.dto.DictCreateDto;
import top.finder.aether.base.core.dto.DictUpdateDto;
import top.finder.aether.base.core.entity.Dict;
import top.finder.aether.base.core.vo.DictVo;

import java.util.List;
import java.util.Set;

/**
 * <p>数据字典服务接口</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
public interface DictService extends IService<Dict> {
    /**
     * <p>新增：字典信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    void create(DictCreateDto createDto);

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
    void update(DictUpdateDto updateDto);

    /**
     * <p>查询：字典信息列表</p>
     *
     * @param dict 查询入参
     * @return {@link List < DictVo >}
     * @author guocq
     * @date 2022/12/29 15:03
     */
    List<DictVo> listQuery(Dict dict);
}
