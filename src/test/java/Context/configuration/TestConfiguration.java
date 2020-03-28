package Context.configuration;


import Context.Book;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestConfiguration {
    @Test
    public void testConfig(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyConfigurationClass.class);//注册@Configuration的类
        applicationContext.refresh();
        Book bean = applicationContext.getBean(Book.class);
        ImportBook importBook = applicationContext.getBean(ImportBook.class);//ImportBook并没有注解，通过被@Import也注册进来了
        ImportInternalObject internalObject = applicationContext.getBean(ImportInternalObject.class);
        importBook.setCity("guangzhou");
        importBook.setCountry("china");
        System.out.println(bean);
        System.out.println(importBook.toString());
        System.out.println(internalObject.toString());
    }

}
