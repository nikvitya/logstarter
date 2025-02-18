package ru.t1.java.logstarter.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.t1.java.logstarter.configuration.HttpLoggingProperties;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class HttpLoggingAspect {
    private final HttpLoggingProperties loggingProperties;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();

        logByType(String.format("Выполняемый метод: %s.%s(), HTTP метод: %s, URI: %s",
                className, methodName, httpMethod, uri));

        Object result = proceedingJoinPoint.proceed();

        if (result == null) {
            result = "Метод не возвращает значения";
        }

        logByType(String.format("Выполненный метод: %s.%s(), HTTP метод: %s, URI: %s, result: %s",
                className, methodName, httpMethod, uri, result));

        return result;
    }

    private void logByType(String message) {
        switch (loggingProperties.getLevel().toUpperCase()) {
            case "INFO" -> log.info(message);
            case "DEBUG" -> log.debug(message);
            case "WARN" -> log.warn(message);
            case "ERROR" -> log.error(message);
            default -> log.debug(message);
        }
    }
}
