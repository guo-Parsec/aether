package top.finder.aether.common.support.api;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.utils.LoggerUtil;

/**
 * <p>统一返回结果封装</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Setter
@Getter
public class Apis<T> {
    private static final Logger log = LoggerFactory.getLogger(Apis.class);
    /**
     * 信息码
     */
    private Integer code;

    /**
     * 数据载体
     */
    private T data;

    /**
     * 返回信息
     */
    private String message;

    Apis() {

    }

    Apis(Integer code) {
        this.code = code;
    }

    Apis(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    Apis(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    Apis(IHttpStatus httpStatus) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
    }

    Apis(IHttpStatus httpStatus, T data) {
        this.code = httpStatus.getCode();
        this.message = httpStatus.getMessage();
        this.data = data;
    }

    Apis(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * <p>成功返回结果</p>
     *
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 9:58
     */
    public static <T> Apis<T> success() {
        return new Apis<>(CommonHttpStatus.SUCCESS);
    }

    /**
     * <p>成功返回结果</p>
     *
     * @param data 返回结果
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 9:58
     */
    public static <T> Apis<T> success(T data) {
        return new Apis<>(CommonHttpStatus.SUCCESS, data);
    }

    /**
     * <p>成功返回结果</p>
     *
     * @param data    返回结果
     * @param message 返回信息
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 9:58
     */
    public static <T> Apis<T> success(T data, String message) {
        return new Apis<>(CommonHttpStatus.SUCCESS.getCode(), data, message);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed() {
        return new Apis<>(CommonHttpStatus.FAILED);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @param httpStatus http状态
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed(IHttpStatus httpStatus) {
        return new Apis<>(httpStatus);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @param httpStatus http状态
     * @param message    返回信息
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed(IHttpStatus httpStatus, String message) {
        return new Apis<>(httpStatus.getCode(), message);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @param code    信息码
     * @param message 返回信息
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed(Integer code, String message) {
        return new Apis<>(code, message);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @param exception 业务异常
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed(AetherException exception) {
        return new Apis<>(exception.getCode(), exception.getMessage());
    }

    /**
     * <p>参数校验失败</p>
     *
     * @param message 返回信息
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:14
     */
    public static <T> Apis<T> validateFailed(String message) {
        return new Apis<>(CommonHttpStatus.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * <p>失败处理返回结果</p>
     *
     * @param exception 业务异常
     * @return {@link Apis <T>}
     * @author guocq
     * @date 2022/12/27 10:02
     */
    public static <T> Apis<T> failed(Exception exception) {
        return new Apis<>(CommonHttpStatus.FAILED.getCode(), exception.getMessage());
    }

    /**
     * <p>获取结果 - 请求失败抛出异常</p>
     *
     * @param apis 请求载体
     * @return T
     * @author guocq
     * @date 2022/12/27 10:08
     */
    public static <T> T getApiData(Apis<T> apis) {
        if (apis == null) {
            throw LoggerUtil.logAetherError(log, "请求结果集为空，可能为请求错误");
        }
        int apiCode = apis.getCode();
        if (!CommonHttpStatus.SUCCESS.getCode().equals(apiCode)) {
            log.error("请求信息码为{}，可能为请求失败", apiCode);
            throw new AetherException(apiCode, apis.getMessage());
        }
        return apis.getData();
    }

    /**
     * <p>获取结果 - 不抛出异常</p>
     *
     * @param apis 请求载体
     * @return T
     * @author guocq
     * @date 2022/12/27 10:08
     */
    public static <T> T getApiDataNoException(Apis<T> apis) {
        if (apis == null) {
            log.error("请求结果集为空，可能为请求错误");
            return null;
        }
        int apiCode = apis.getCode();
        if (!CommonHttpStatus.SUCCESS.getCode().equals(apiCode)) {
            log.error("请求信息码为{}，可能为请求失败", apiCode);
            return null;
        }
        return apis.getData();
    }
}
