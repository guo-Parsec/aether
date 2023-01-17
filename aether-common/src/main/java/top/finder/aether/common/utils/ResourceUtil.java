package top.finder.aether.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p>资源工具类</p>
 *
 * @author guocq
 * @since 2023/1/17
 */
public class ResourceUtil {
    private static final Logger log = LoggerFactory.getLogger(ResourceUtil.class);

    /**
     * 获取资源路径
     *
     * @param path 路径
     */
    public static File getResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            return file;
        } catch (FileNotFoundException e) {
            throw LoggerUtil.logAetherError(log, "获取资源文件[{}]失败", path);
        }
    }

    /**
     * <p>获取resource目录下的templates目录</p>
     *
     * @return java.io.File
     * @author guocq
     * @date 2023/1/17 10:13
     */
    public static File getTemplates() {
        File templates = getResource("templates");
        if (templates.isFile()) {
            throw LoggerUtil.logAetherError(log, "获取资源文件[{}]失败", "templates");
        }
        return templates;
    }
}
