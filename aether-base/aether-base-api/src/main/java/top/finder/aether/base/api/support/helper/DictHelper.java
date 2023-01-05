package top.finder.aether.base.api.support.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.base.api.client.DictClient;
import top.finder.aether.base.api.model.DictModel;
import top.finder.aether.common.support.annotation.DictTranslate;
import top.finder.aether.common.support.annotation.DictValid;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.ReflectHelper;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.common.support.helper.TransformerHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>数据字典辅助类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
public class DictHelper {
    private static final Logger log = LoggerFactory.getLogger(DictHelper.class);

    /**
     * DictTranslate类对象
     */
    private static final Class<DictTranslate> DICT_TRANSLATE_CLASS = DictTranslate.class;

    /**
     * DictValid类对象
     */
    private static final Class<DictValid> DICT_VALID_CLASS = DictValid.class;

    /**
     * DictClient对象
     */
    private static final DictClient DICT_CLIENT = SpringBeanHelper.getBean(DictClient.class);

    /**
     * 字典数据缓存
     */
    private static final Map<String, List<DictModel>> CACHED_DICT = Maps.newHashMapWithExpectedSize(10);

    /**
     * <p>对目标对象{@code target}进行字典转义</p>
     *
     * @param target 目标对象
     * @author guocq
     * @date 2022/12/14 8:59
     */
    public static void translate(Object target) {
        if (ObjectUtil.isEmpty(target)) {
            log.warn("目标对象[target]不能为空");
            return;
        }
        Class<Object> targetClass = ClassUtil.getClass(target);
        Field[] fields = ReflectHelper.getFields(targetClass, field -> field.isAnnotationPresent(DICT_TRANSLATE_CLASS));
        if (ArrayUtil.isEmpty(fields)) {
            log.debug("目标对象[target={}]中不存在需要字典转义的属性", target);
            return;
        }
        AtomicReference<DictTranslate> dictTranslate = new AtomicReference<>();
        Arrays.stream(fields).forEach(field -> {
            dictTranslate.set(field.getAnnotation(DICT_TRANSLATE_CLASS));
            String fieldName = dictTranslate.get().value();
            String dictTypeCode = dictTranslate.get().type();
            Object dictCodeObject = ReflectHelper.getFieldValue(target, fieldName);
            Integer dictCode = dictCodeObject instanceof Integer ? (Integer) dictCodeObject : null;
            String dictName = translateCore(dictTypeCode, dictCode);
            ReflectHelper.setFieldValue(target, field, dictName);
        });
    }

    /**
     * <p>对象互转并转义</p>
     *
     * @param source 源对象
     * @param tClass 目标对象class
     * @return T
     * @author guocq
     * @date 2023/1/5 17:46
     */
    public static <S, T> T transformerAndTranslate(S source, Class<T> tClass) {
        T t = TransformerHelper.transformer(source, tClass);
        translate(t);
        return t;
    }

    /**
     * <p>核心转义</p>
     *
     * @param dictTypeCode 字典类别码值
     * @param dictCode     字典码值
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/29 14:55
     */
    private static String translateCore(String dictTypeCode, Integer dictCode) {
        List<DictModel> dictModels = CACHED_DICT.get(dictTypeCode);
        if (CollUtil.isEmpty(dictModels)) {
            dictModels = Optional.ofNullable(Apis.getApiDataNoException(DICT_CLIENT.findDictListByType(dictTypeCode)))
                    .orElse(Lists.newArrayList());
            CACHED_DICT.put(dictTypeCode, dictModels);
        }
        Optional<DictModel> optional = dictModels.stream().filter(model -> ObjectUtil.equals(dictCode, model.getDictCode())).findFirst();
        return optional.map(DictModel::getDictName).orElse(null);
    }

    /**
     * <p>根据指定key清空缓存</p>
     *
     * @param key 指定key
     * @author guocq
     * @date 2022/12/29 14:56
     */
    public static void clearCache(String key) {
        if (StrUtil.isBlank(key)) {
            log.warn("key不能为空");
            return;
        }
        if (!CACHED_DICT.containsKey(key)) {
            log.warn("key[{}]不存在", key);
            return;
        }
        CACHED_DICT.remove(key);
    }

    /**
     * <p>清空缓存</p>
     *
     * @author guocq
     * @date 2022/12/29 14:56
     */
    public static void clearCache() {
        CACHED_DICT.clear();
    }

    /**
     * <p>校验字典属性是否合法</p>
     *
     * @param target 目标对象
     * @author guocq
     * @date 2023/1/5 14:12
     */
    public static void verifyDictLegitimacy(final Object target) {
        if (ObjectUtil.isEmpty(target)) {
            log.warn("目标对象[target]不能为空");
            return;
        }
        final Class<Object> targetClass = ClassUtil.getClass(target);
        final Set<Field> fields = Sets.newHashSet(ReflectHelper.getFields(targetClass, field -> field.isAnnotationPresent(DICT_VALID_CLASS)));
        if (ArrayUtil.isEmpty(fields)) {
            log.debug("目标对象[target={}]中字典属性", target);
            return;
        }
        fields.forEach(field -> validFieldDict(field, target));
    }

    /**
     * <p>校验字典属性是否合法</p>
     *
     * @param field  属性对象
     * @param target 目标对象
     * @author guocq
     * @date 2023/1/5 14:11
     */
    private static void validFieldDict(Field field, Object target) {
        String fieldName = field.getName();
        DictValid dictValid = field.getAnnotation(DICT_VALID_CLASS);
        String dictTypeCode = dictValid.type();
        boolean emptyValid = dictValid.emptyValid();
        String fieldValue = StrUtil.toStringOrNull(ReflectHelper.getFieldValue(target, field));
        if (ObjectUtil.isEmpty(fieldValue)) {
            if (emptyValid) {
                CodeHelper.logAetherValidError(log, "字段[fieldName={}]为空，校验失败", fieldName);
            }
            return;
        }
        Integer dictCode = NumberUtil.parseInt(fieldValue);
        String dictValue = translateCore(dictTypeCode, dictCode);
        if (StrUtil.isBlank(dictValue)) {
            CodeHelper.logAetherValidError(log, "字段[fieldName={},dictTypeCode={},dictCode={}]找不到对应的字典值校验失败", fieldName, dictTypeCode, dictCode);
        }
    }
}


