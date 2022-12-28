package top.finder.aether.data.security.support.helper;

import cn.hutool.json.JSONUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.api.IHttpStatus;

/**
 * <p>webflux相关辅助类</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public class WebfluxHelper {

    /**
     * <p>失败写入</p>
     *
     * @param response 响应
     * @param message  错误信息
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> failedWrite(ServerHttpResponse response, String message) {
        return failedWrite(response, message, CommonHttpStatus.FAILED);
    }

    /**
     * <p>鉴权失败写入</p>
     *
     * @param response 响应
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> unauthorizedWrite(ServerHttpResponse response) {
        return failedWrite(response, CommonHttpStatus.UNAUTHORIZED);
    }

    /**
     * <p>鉴权失败写入</p>
     *
     * @param response 响应
     * @param message  错误信息
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> unauthorizedWrite(ServerHttpResponse response, String message) {
        return failedWrite(response, message, CommonHttpStatus.UNAUTHORIZED);
    }

    /**
     * <p>失败写入</p>
     *
     * @param response   响应
     * @param httpStatus 错误码枚举
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> failedWrite(ServerHttpResponse response, IHttpStatus httpStatus) {
        return failedWrite(response, httpStatus.getMessage(), httpStatus);
    }

    /**
     * <p>失败写入</p>
     *
     * @param response   响应
     * @param message    错误信息
     * @param httpStatus 错误码枚举
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> failedWrite(ServerHttpResponse response, String message, IHttpStatus httpStatus) {
        return failedWrite(response, HttpStatus.OK, message, httpStatus);
    }

    /**
     * <p>失败写入</p>
     *
     * @param response   响应
     * @param status     http状态
     * @param message    错误信息
     * @param httpStatus 错误码枚举
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> failedWrite(ServerHttpResponse response, HttpStatus status, String message, IHttpStatus httpStatus) {
        return failedWrite(response, MediaType.APPLICATION_JSON_VALUE, status, message, httpStatus);
    }

    /**
     * <p>失败写入</p>
     *
     * @param response    响应
     * @param contentType HTTP Content-Type标头字段
     * @param status      http状态
     * @param message     错误信息
     * @param httpStatus  错误码枚举
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author guocq
     * @date 2022/12/28 10:31
     */
    public static Mono<Void> failedWrite(ServerHttpResponse response, String contentType, HttpStatus status, String message, IHttpStatus httpStatus) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        Apis<Void> apis = Apis.failed(httpStatus, message);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(apis).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
