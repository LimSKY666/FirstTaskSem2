package ru.kpfu.itis.sokolov.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.sokolov.dto.UserDto;

@Component
@Aspect
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

//    @Pointcut("execution(* ru.kpfu.itis.sokolov.controller.WeatherController.addWeather())")
//    public void logUserWhoSendRequest() {
//    }

    @Pointcut("@annotation(Loggable)")
    public void logUserWhoSendRequest() {

    }

    @After("logUserWhoSendRequest()")
    public Object logAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UserDto user = (UserDto) proceedingJoinPoint.getArgs()[1];

        Object result = proceedingJoinPoint.proceed();

        LOGGER.info("User send request. ID: {}, Email: {}", user.getId(), user.getEmail());

        return result;
    }
}
