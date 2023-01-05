package top.finder.aether.data.common.entity;

/**
 * <p>分页参数</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
public interface IPageDto {
    /**
     * <p>获取当前页</p>
     *
     * @return java.lang.Integer
     * @author guocq
     * @date 2023/1/4 11:10
     */
    Integer getCurrentPage();

    /**
     * <p>获取页大小</p>
     *
     * @return java.lang.Integer
     * @author guocq
     * @date 2023/1/4 11:10
     */
    Integer getPageSize();
}
