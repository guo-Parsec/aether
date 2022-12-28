package top.finder.aether.base.api.support.helper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.annotation.DictTranslate;
import top.finder.aether.common.support.helper.ReflectHelper;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * <p>数据字典辅助类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
public class DictHelper {
    private static final Logger log = LoggerFactory.getLogger(DictHelper.class);

    private static final Class<DictTranslate> DICT_TRANSLATE_CLASS = DictTranslate.class;

    /**
     * <p>对目标对象{@code target}进行字典转义</p>
     *
     * @param target 目标对象
     * @return void
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
        Arrays.stream(fields).forEach(field -> {
            DictTranslate dictTranslate = field.getAnnotation(DICT_TRANSLATE_CLASS);
            String fieldName = dictTranslate.value();
            String dictType = dictTranslate.type();
            Object dictValueObject = ReflectHelper.getFieldValue(target, fieldName);
            Integer dictValue = dictValueObject instanceof Integer ? (Integer) dictValueObject : null;
            // todo 根据 dictType 和 dictValue转义字典
            String dictName = mockDictTranslateCore(dictType, dictValue);
            ReflectHelper.setFieldValue(target, field, dictName);
        });
    }

    private static String mockDictTranslateCore(String dictType, Integer dictValue) {
        String dictName = "";
        switch (dictType) {
            case "sex": {
                switch (dictValue) {
                    case 0:
                        dictName = "未知";
                        break;
                    case 1:
                        dictName = "男性";
                        break;
                    case 2:
                        dictName = "女性";
                        break;
                    default:
                        dictName = null;
                        break;
                }
                break;
            }
            case "user_type": {
                switch (dictValue) {
                    case 0:
                        dictName = "系统用户";
                        break;
                    case 1:
                        dictName = "注册用户";
                        break;
                    case 2:
                        dictName = "临时用户";
                        break;
                    default:
                        dictName = null;
                        break;
                }
                break;
            }
            default:
                dictName = null;
                break;

        }
        return dictName;
    }
}


