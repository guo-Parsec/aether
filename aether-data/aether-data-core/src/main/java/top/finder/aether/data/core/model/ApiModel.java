package top.finder.aether.data.core.model;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.common.support.helper.UrlHelper;
import top.finder.aether.data.core.support.annotation.ApiResource;
import top.finder.aether.data.core.support.enums.ResourceType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * <p>api模型</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Setter
@Getter
public class ApiModel {
    /**
     * 唯一标识
     */
    private String id;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 资源码
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源uri
     */
    private Set<String> uri;

    /**
     * 资源排序
     */
    private Integer sort;

    /**
     * 资源描述
     */
    private String desc;

    /**
     * 资源类别
     */
    private ResourceType resourceType;

    public ApiModel() {
    }

    ApiModel(Class<?> apiClass, Method method) {
        this.methodName = method.getName();
        this.id = apiClass.getName() + "#" + this.methodName;
    }

    public ApiModel completeUri(String prefix) {
        this.uri = this.uri.stream().map(path -> {
            String finalPath = UrlHelper.autoCompletePath(prefix) + UrlHelper.autoCompletePath(path);
            return StrUtil.isBlank(finalPath) ? UrlHelper.REQ_PATH_SEPARATOR : finalPath;
        }).collect(Collectors.toSet());
        return this;
    }

    public static ApiModel of(Class<?> apiClass, Method method) {
        if (apiClass == null || method == null) {
            throw new IllegalArgumentException("apiClass和method不能为空");
        }
        ApiResource apiResource = method.getAnnotation(ApiResource.class);
        if (apiResource == null) {
            throw new IllegalArgumentException("当前方法不支持资源处理");
        }
        ApiModel apiModel = new ApiModel(apiClass, method);
        apiModel.setResourceCode(apiResource.code());
        apiModel.setResourceName(apiResource.name());
        Set<String> uriSet = findUri(apiClass, method);
        apiModel.setUri(uriSet);
        apiModel.setSort(apiResource.sort());
        apiModel.setDesc(apiResource.desc());
        apiModel.setResourceType(apiResource.resourceType());
        return apiModel;
    }

    private static Set<String> findUri(Class<?> apiClass, Method method) {
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(apiClass, RequestMapping.class);
        String[] prefixPathArray = null;
        if (requestMapping != null && ArrayUtil.isNotEmpty(requestMapping.value())) {
            prefixPathArray = requestMapping.value();
        }
        Set<String> methodUriSet = Sets.newHashSet(getMethodUri(method));
        Set<String> prefixPathSet = prefixPathArray == null ? Sets.newHashSet() : Sets.newHashSet(prefixPathArray);
        Set<String> result = Sets.newHashSet();
        methodUriSet.forEach(methodUri -> {
            prefixPathSet.forEach(prefixUri -> {
                String path = UrlHelper.autoCompletePath(prefixUri) + UrlHelper.autoCompletePath(methodUri);
                result.add(StrUtil.isBlank(path) ? UrlHelper.REQ_PATH_SEPARATOR : path);
            });
        });
        return result;
    }

    /**
     * <p>获取方法uri</p>
     *
     * @param method 方法
     * @return java.lang.String[]
     * @author guocq
     * @date 2023/1/10 10:25
     */
    private static String[] getMethodUri(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
                RequestMapping request = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                return request == null ? new String[0] : request.value();
            }
            if (annotation instanceof GetMapping) {
                GetMapping get = AnnotationUtils.findAnnotation(method, GetMapping.class);
                return get == null ? new String[0] : get.value();
            }
            if (annotation instanceof PostMapping) {
                PostMapping post = AnnotationUtils.findAnnotation(method, PostMapping.class);
                return post == null ? new String[0] : post.value();
            }
            if (annotation instanceof PutMapping) {
                PutMapping put = AnnotationUtils.findAnnotation(method, PutMapping.class);
                return put == null ? new String[0] : put.value();
            }
            if (annotation instanceof DeleteMapping) {
                DeleteMapping delete = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
                return delete == null ? new String[0] : delete.value();
            }
        }
        return new String[0];
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiModel.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("uri=" + uri)
                .toString();
    }
}
