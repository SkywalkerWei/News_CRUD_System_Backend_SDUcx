package redlib.backend.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常类
 * 用于在业务逻辑中抛出可预期的异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 1000; // 与 MyControllerAdvice 中的默认错误码保持一致
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}