package top.finder.aether.common.support.exception;

import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.api.IHttpStatus;

/**
 * <p>业务校验异常</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public class AetherValidException extends AetherException {
    private static final long serialVersionUID = -7439577983194784260L;


    public AetherValidException(IHttpStatus httpStatus) {
        super(httpStatus);
    }

    public AetherValidException(String message) {
        super(CommonHttpStatus.VALIDATE_FAILED, message);
    }

    public AetherValidException(Throwable cause, IHttpStatus httpStatus) {
        super(cause, httpStatus);
    }

    public AetherValidException(Throwable cause, String message) {
        super(cause, message);
    }

    public AetherValidException(IHttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public AetherValidException(Integer code, String message) {
        super(code, message);
    }
}
