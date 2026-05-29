package io.github.yiyongbo.scaffold.common.config.web;

import io.github.yiyongbo.scaffold.common.exception.BizException;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.common.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author kidd
 * @since 2026/5/28 23:02
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException ex, HttpServletRequest request) {
        log.warn("业务异常：request={}, code={}, message={}", requestInfo(request), ex.getCode(), ex.getMessage());

        return Result.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理 @RequestBody 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        log.warn("请求体参数校验失败：request={}, message={}", requestInfo(request), message);

        return Result.fail(CommonResponseCode.PARAM_ERROR, message);
    }

    /**
     * 处理表单参数 / query 参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        log.warn("请求参数绑定失败：request={}, message={}", requestInfo(request), message);

        return Result.fail(CommonResponseCode.PARAM_ERROR, message);
    }

    /**
     * 处理单个 query/path 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        log.warn("请求参数校验失败：request={}, message={}", requestInfo(request), message);

        return Result.fail(CommonResponseCode.PARAM_ERROR, message);
    }

    /**
     * 处理请求参数类型转换异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String requiredType = ex.getRequiredType() == null ? "unknown" : ex.getRequiredType().getSimpleName();
        log.warn("请求参数格式错误：request={}, name={}，value={}，requiredType={}", requestInfo(request), ex.getName(), ex.getValue(), requiredType);

        return Result.fail(CommonResponseCode.PARAM_ERROR, "请求参数格式错误：" + ex.getName());
    }

    /**
     * 处理请求体读取异常，例如 JSON 格式错误、字段类型错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("请求体格式错误：request={}, message={}", requestInfo(request), ex.getMessage());

        return Result.fail(CommonResponseCode.PARAM_ERROR, "请求体格式错误");
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Void> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("请求方法不支持：request={}, message={}", requestInfo(request), ex.getMessage());

        return Result.fail(CommonResponseCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 处理请求媒体类型不支持异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result<Void> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.warn("请求媒体类型不支持：request={}, contentType={}，supportedMediaTypes={}", requestInfo(request), ex.getContentType(), ex.getSupportedMediaTypes());

        return Result.fail(CommonResponseCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 处理静态资源不存在异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<Void> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        log.debug("请求资源不存在：request={}, resourcePath={}", requestInfo(request), ex.getResourcePath());

        return Result.fail(CommonResponseCode.NOT_FOUND);
    }

    /**
     * 处理请求路径不存在异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Void> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        log.debug("请求路径不存在：request={}, url={}", requestInfo(request), ex.getRequestURL());

        return Result.fail(CommonResponseCode.NOT_FOUND);
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception ex, HttpServletRequest request) {
        log.error("系统未知异常：request={}", requestInfo(request), ex);

        return Result.fail(CommonResponseCode.SYSTEM_ERROR);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }

    private String requestInfo(HttpServletRequest request) {
        return request.getMethod() + " " + request.getRequestURI();
    }

}
