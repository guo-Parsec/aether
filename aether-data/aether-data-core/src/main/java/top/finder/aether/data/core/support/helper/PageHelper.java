package top.finder.aether.data.core.support.helper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.finder.aether.data.common.entity.BaseEntity;
import top.finder.aether.data.common.entity.IPageDto;

/**
 * <p>分页辅助类</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
public class PageHelper {
    /**
     * <p>初始化分页参数</p>
     *
     * @param dto 入参
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/5 17:36
     */
    public static <T extends BaseEntity> IPage<T> initPage(IPageDto dto) {
        return new Page<>(dto.getCurrentPage(), dto.getPageSize());
    }
}
