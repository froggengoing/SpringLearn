package Context.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class AOPMainIntefacce implements Advisor {

    @Override
    public Advice getAdvice() {
        BeforeAdvice beforeAdvice = new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("Advisor接口增强方法"+method+"执行前");
            }
        };

        return beforeAdvice;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
