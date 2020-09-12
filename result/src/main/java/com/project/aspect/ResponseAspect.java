package com.project.aspect;

import com.project.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseAspect {
    @Pointcut("@annotation(com.project.annotation.ResAspectAnno)")
    public void responsePointCut() {}

    @Around(value = "responsePointCut()")
    public Object afterBodyWrite(ProceedingJoinPoint pjp) throws Throwable {
        // 防止重复包裹的问题出现
        Object body = pjp.proceed();//执行下一步操作
        if (body instanceof Result) {
            return body;
        }

        return Result.success(body);
    }
}
