package Context.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPMain {

    @Pointcut("@annotation(Context.aop.log)")
    public void log(){
    }

    @Before("log()")
    public void beforaop(){
        System.out.println("before前置增强");
    }

    @After("log()")
    public void afteraop(){
        System.out.println("afteraop后置增强");
    }

    @AfterReturning("execution(* Context.aop.people.*(..))")
    public void aterReturingaop(){
        System.out.println("afterReturn");
    }
}
