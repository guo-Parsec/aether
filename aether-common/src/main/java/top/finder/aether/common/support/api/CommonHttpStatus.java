package top.finder.aether.common.support.api;

import lombok.Getter;

/**
 * <p>http通用状态</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public enum CommonHttpStatus implements IHttpStatus {
    /**
     * 成功状态
     */
    SUCCESS,
    /**
     * 系统失败
     */
    FAILED(500, "系统错误"),

    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(400, "参数检验失败"),

    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    /**
     * 暂未登录或token已经过期
     */
    ILLEGAL_REQUESTS(402, "非法请求，当前请求无法访问"),

    ;
    /**
     * 状态码
     */
    @Getter
    private final Integer code;
    /**
     * 状态信息
     */
    @Getter
    private final String message;

    CommonHttpStatus() {
        this.code = 200;
        this.message = "操作成功";
    }

    CommonHttpStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
