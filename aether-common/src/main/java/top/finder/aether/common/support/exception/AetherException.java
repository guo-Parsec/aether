package top.finder.aether.common.support.exception;

import lombok.Getter;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.api.IHttpStatus;

/**
 * <p>业务异常</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public class AetherException extends RuntimeException {
    private static final long serialVersionUID = -7439577983194784260L;

    @Getter
    private final Integer code;
    @Getter
    private final String message;

    public AetherException(IHttpStatus httpStatus) {
        this(httpStatus.getCode(), httpStatus.getMessage());
    }

    public AetherException(String message) {
        this(CommonHttpStatus.FAILED, message);
    }

    public AetherException(Throwable cause, IHttpStatus httpStatus) {
        this(httpStatus, cause.getMessage());
    }

    public AetherException(Throwable cause) {
        this(cause, CommonHttpStatus.FAILED);
    }

    public AetherException(Throwable cause, String message) {
        super(cause);
        this.message = message;
        this.code = CommonHttpStatus.FAILED.getCode();
    }

    public AetherException(IHttpStatus httpStatus, String message) {
        this(httpStatus.getCode(), message);
    }

    public AetherException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
