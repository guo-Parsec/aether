package top.finder.aether.base.api.tools;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.*;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.base.api.facade.SysDictFacade;
import top.finder.aether.base.api.holders.SysDictHolders;
import top.finder.aether.common.support.annotation.DictTranslate;
import top.finder.aether.common.support.annotation.DictValid;
import top.finder.aether.common.support.helper.ReflectHelper;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.LoggerUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>dict工具类</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public class DictTool {
    private static final Logger log = LoggerFactory.getLogger(DictTool.class);

    private static final class DictAccessHolder {
        static final SysDictFacade DICT_ACCESS = SpringUtil.getBean(SysDictFacade.class);
    }

    /**
     * <p>对目标对象中被注解{@link DictTranslate}标识的字段进行字典翻译</p>
     *
     * @param target 目标对象
     * @author guocq
     * @date 2023/1/11 10:19
     */
    public static void translate(Object target) {
        if (ObjectUtil.isEmpty(target)) {
            log.warn("目标对象[target]不能为空");
            return;
        }
        Set<Field> dictFields = findDictFields(ClassUtil.getClass(target), DictTranslate.class);
        setDictFieldValue(target, dictFields);
    }

    /**
     * <p>将源对象{@code source}转换为指定类型{@link T},对转换后的对象{@code target}中被注解{@link DictTranslate}标识的字段进行字典翻译</p>
     *
     * @param source 源对象
     * @param tClass 目标对象class
     * @return T 目标类型
     * @author guocq
     * @date 2023/1/11 10:20
     */
    public static <S, T> T transformerAndTranslate(S source, Class<T> tClass) {
        T target = TransformerHelper.transformer(source, tClass);
        translate(target);
        return target;
    }

    /**
     * <p>设置字典属性的值</p>
     *
     * @param object 目标对象
     * @param fields 字典属性集合
     * @author guocq
     * @date 2023/1/11 10:17
     */
    private static void setDictFieldValue(Object object, Set<Field> fields) {
        final AtomicReference<DictTranslate> dictTranslate = new AtomicReference<>();
        fields.forEach(field -> {
            dictTranslate.set(field.getAnnotation(DictTranslate.class));
            String dictFieldName = dictTranslate.get().value();
            Object dictCodeObject = ReflectHelper.getFieldValue(object, dictFieldName);
            Integer dictCode = null;
            try {
                dictCode = Integer.valueOf(StrUtil.toStringOrNull(dictCodeObject));
            } catch (Exception e) {
                log.warn("目标对象[object={}]的属性[{}]值[{}]转化为Integer类型失败", object, field.getName(), dictCodeObject);
                return;
            }
            String dictTypeCode = dictTranslate.get().type();
            String dictName = findDictName(dictTypeCode, dictCode);
            ReflectHelper.setFieldValue(object, field, dictName);
        });
    }

    /**
     * <p>获取指定类的被{@link DictTranslate}标识的字段集合</p>
     *
     * @param clazz 指定类
     * @return java.util.Set<java.lang.reflect.Field>
     * @author guocq
     * @date 2023/1/11 10:03
     */
    private static Set<Field> findDictFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Field[] fields = ReflectHelper.getFields(clazz, field -> field.isAnnotationPresent(annotationClass));
        if (ArrayUtil.isEmpty(fields)) {
            log.debug("检索类[{}]中没有被注解[{}]标识的字段", clazz.getName(), annotationClass.getSimpleName());
            return Sets.newHashSet();
        }
        return Sets.newHashSet(fields);
    }

    /**
     * <p>获取数据字典名称</p>
     *
     * @param dictTypeCode 数据字典类型
     * @param dictCode     数据字典码
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/11 9:59
     */
    private static String findDictName(String dictTypeCode, Integer dictCode) {
        Optional<SysDictHolders> optional = DictAccessHolder.DICT_ACCESS.findDictByTypeAndCode(dictTypeCode, dictCode);
        return optional.map(SysDictHolders::getDictName).orElse(null);
    }

    /**
     * <p>校验目标对象中的字典属性是否合法</p>
     *
     * @param target 目标对象
     * @author guocq
     * @date 2023/1/11 10:30
     */
    public static void verifyDictLegitimacy(final Object target) {
        if (ObjectUtil.isEmpty(target)) {
            log.warn("目标对象[target]不能为空");
            return;
        }
        Set<Field> fields = findDictFields(ClassUtil.getClass(target), DictValid.class);
        if (CollUtil.isEmpty(fields)) {
            log.debug("目标对象[target]中没有需要校验的字典属性");
            return;
        }
        fields.forEach(field -> validFieldDict(field, target));
    }

    /**
     * <p>校验字典属性的值是否合法</p>
     *
     * @param field  字典属性
     * @param object 目标对象
     * @author guocq
     * @date 2023/1/11 10:30
     */
    private static void validFieldDict(Field field, Object object) {
        String fieldName = field.getName();
        DictValid dictValid = field.getAnnotation(DictValid.class);
        String dictTypeCode = dictValid.type();
        String fieldValue = StrUtil.toStringOrNull(ReflectHelper.getFieldValue(object, field));
        if (ObjectUtil.isEmpty(fieldValue)) {
            if (dictValid.emptyValid()) {
                throw LoggerUtil.logAetherError(log, "字段[fieldName={}]为空，校验失败", fieldName);
            }
            return;
        }
        Integer dictCode = NumberUtil.parseInt(fieldValue);
        String dictValue = findDictName(dictTypeCode, dictCode);
        if (StrUtil.isBlank(dictValue)) {
            throw LoggerUtil.logAetherError(log, "字段[fieldName={},dictTypeCode={},dictCode={}]找不到对应的字典值校验失败", fieldName, dictTypeCode, dictCode);
        }
    }
}
