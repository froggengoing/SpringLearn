package Context.BeanFacotryPostProcess;


import com.froggengo.springlearn.SpringLearnMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringLearnMain.class)
public class TestBFPP {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 打印默认的 BeanFactoryPostProcessor
     */
    @Test
    public void testPrintBFPP(){
        GenericWebApplicationContext webpplicationContext = (GenericWebApplicationContext) this.applicationContext;
        //webpplicationContext.getBeanFactoryPostProcessors().forEach(n-> System.out.println(n));
        ConfigurableListableBeanFactory beanFactory = webpplicationContext.getBeanFactory();
        Arrays.stream(beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class)).forEach(
                n-> System.out.println(String.format("%80s",n)+"----"+beanFactory.getBean(n).getClass().toString())

        );
    }

    /**
     * 打印默认的BeanPostProcessor
     * AnnotationAwareAspectJAutoProxyCreator
     */
    @Test
    public void testPrintBPP(){
        GenericWebApplicationContext webpplicationContext = (GenericWebApplicationContext) this.applicationContext;
        //webpplicationContext.getBeanFactoryPostProcessors().forEach(n-> System.out.println(n));
        ConfigurableListableBeanFactory beanFactory = webpplicationContext.getBeanFactory();
        Arrays.stream(beanFactory.getBeanNamesForType(BeanPostProcessor.class)).forEach(
                n-> System.out.println(String.format("%80s",n)+"----"+beanFactory.getBean(n).getClass().toString())

        );
    }


}
