package top.finder.aether.common.support.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import java.util.Collection;

/**
 * <p></p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public class UrlHelper extends URLUtil {

    public static final String REQ_PATH_SEPARATOR = "/";
    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return 是否匹配
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str           指定字符串
     * @param strCollection 需要检查的字符串数组
     * @return boolean 是否匹配
     * @author guochengqiang
     */
    public static boolean matches(String str, Collection<String> strCollection) {
        if (StrUtil.isBlank(str) || CollUtil.isEmpty(strCollection)) {
            return false;
        }
        return strCollection.stream().anyMatch(pattern -> isMatch(pattern, str));
    }

    /**
     * <p>自动填充请求根路径</p>
     *
     * @param orgPath 原始路径
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 19:28
     */
    public static String autoPopulateRequestRootPath(String orgPath) {
        return orgPath.startsWith(REQ_PATH_SEPARATOR) ? orgPath : REQ_PATH_SEPARATOR + orgPath;
    }
}
