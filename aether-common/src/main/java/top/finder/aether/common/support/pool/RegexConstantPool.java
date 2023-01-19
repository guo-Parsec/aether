package top.finder.aether.common.support.pool;

import java.util.regex.Pattern;

/**
 * <p>正则常量池</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
public interface RegexConstantPool {
    /**
     * 只能匹配英文字母的正则字符串
     */
    String STR_ONLY_ENGLISH_LETTERS = "^[A-Za-z]+$";

    /**
     * 只能匹配英文字母的正则提示信息
     */
    String MSG_ONLY_ENGLISH_LETTERS = "只能匹配英文字母";

    /**
     * 只能匹配英文字母的正则表达式
     */
    Pattern REG_ONLY_ENGLISH_LETTERS = Pattern.compile(STR_ONLY_ENGLISH_LETTERS);

    /**
     * 只能匹配英文字母及数字的正则字符串
     */
    String STR_LETTERS_AND_NUMBERS = "^[A-Za-z0-9]+$";

    /**
     * 只能匹配英文字母及数字的正则提示信息
     */
    String MSG_LETTERS_AND_NUMBERS = "只能匹配英文字母及数字";

    /**
     * 只能匹配英文字母及数字的正则表达式
     */
    Pattern REG_LETTERS_AND_NUMBERS = Pattern.compile(STR_LETTERS_AND_NUMBERS);

    /**
     * 只能匹配英文字母、数字以及下划线的正则字符串
     */
    String STR_WORDS = "^\\w+$";

    /**
     * 只能匹配英文字母、数字以及下划线的正则提示信息
     */
    String MSG_WORDS = "只能匹配英文字母、数字以及下划线";

    /**
     * 只能匹配英文字母、数字以及下划线的正则表达式
     */
    Pattern REG_WORDS = Pattern.compile(STR_WORDS);

    /**
     * 只能匹配中文的正则字符串
     */
    String STR_ONLY_CHINESE = "^[\\u4e00-\\u9fa5]*$";

    /**
     * 只能匹配中文的正则提示信息
     */
    String MSG_ONLY_CHINESE = "只能匹配中文";

    /**
     * 只能匹配中文的正则表达式
     */
    Pattern REG_ONLY_CHINESE = Pattern.compile(STR_ONLY_CHINESE);
}
