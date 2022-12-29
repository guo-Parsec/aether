package top.finder.aether.base.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.finder.aether.base.api.model.DictModel;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;

import java.util.List;

/**
 * <p>数据字典对外服务接口</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@FeignClient(name = AppConstantPool.APP_BASE + BaseApiConstantPool.DICT_WEB_API_PREFIX, contextId = "dictClient")
public interface DictClient {
    /**
     * <p>根据字典类别码值查询字典列表</p>
     *
     * @param dictTypeCode 字典类别码值
     * @return 字典类别码值
     * @author guocq
     * @date 2022/12/29 14:39
     */
    @GetMapping(value = "/find-dict-list-by-type")
    Apis<List<DictModel>> findDictListByType(@RequestParam("dictTypeCode") String dictTypeCode);
}
