package Context.scanner;

import com.froggengo.springlearn.SpringLearnMain;
import com.froggengo.springlearn.project.controller.UserController;
import org.junit.Test;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Arrays;

public class TestScanner {

    /**
     * 1、怎么扫描指定包下的类，添加至applicationContext中
     * 扫描指定包名下的类@component和@ManagedBean的类，包括继承@Component的注解比如@Component
     */
    @Test
    public void testScanner() {
        AnnotationConfigServletWebServerApplicationContext applicationContext = new AnnotationConfigServletWebServerApplicationContext();
        ClassPathBeanDefinitionScanner definitionScanner = new ClassPathBeanDefinitionScanner(applicationContext);
        String s = ClassUtils.convertClassNameToResourcePath(applicationContext.getEnvironment().resolveRequiredPlaceholders("com.froggengo.springlearn.project"));
        int scan = definitionScanner.scan("com.froggengo.springlearn.project");
        System.out.println(scan);
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(n ->
                System.out.println(applicationContext.getBeanDefinition(n).getBeanClassName()));
        ///1、解析basepackage路径，转换为文件路径，进而获取该路径下所有文件
        //解析包名等价于PathMatchingResourcePatternResolver.getResources(classpath*:com/froggengo/springlearn/project/**/*.class)
        //需要ResourcePatternResolver解析，但applicationcontext已经本身实现了这个接口
        //new AntPathMatcher();
        //当前的类加载器获取cl.getResources(path) : ClassLoader.getSystemResources(path));解析为URL
        //URL [file:/F:/dev/SpringLearn/target/classes/com/froggengo/springlearn/project/]
        //2、取出Component及 ManagedBean 注解的类
        //org.springframework.core.type.classreading.MetadataReaderFactory.
        // MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
        //isCandidateComponent(metadataReader)，包括interface org.springframework.stereotype.Component 及interface javax.annotation.ManagedBean
        //ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
    }

    /**
     * 2、什么时候扫描，怎么控制
     * {@link org.springframework.context.annotation.ConfigurationClassParser}
     * {@link org.springframework.context.annotation.ComponentScanAnnotationParser.parse()}
     *
     * {@link org.springframework.context.annotation.ConfigurationClassPostProcessor}
     *{beanFactory.getBean("org.springframework.context.annotation.internalConfigurationAnnotationProcessor", BeanDefinitionRegistryPostProcessor.class)}
     * {@link org.springframework.context.support.PostProcessorRegistrationDelegate} (选出BeanDefinitionRegistryPostProcessor接口实现执行)#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.List)
     * org.springframework.context.support.AbstractApplicationContext#refresh()
     * {@link AbstractApplicationContext#beanFactoryPostProcessors}
     * {@link AbstractBeanFactory#beanPostProcessors}
     *
     *
     * 疑问：什么时候怎么扫jar包的类
     */
    /**
     *  ClassUtils.isAssignable(clazz, other){@link org.springframework.core.PriorityOrdered},{@link org.springframework.context.annotation.ConfigurationClassPostProcessor}
     * {@link ConfigurationClassUtils.isFullConfigurationClass(beanDef)} 好像没用{@link ConfigurationClassPostProcessor#processConfigBeanDefinitions(org.springframework.beans.factory.support.BeanDefinitionRegistry)}
     *ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)对启动类为true
     *
     * AnnotatedElementUtils.isAnnotated(getIntrospectedClass(), annotationName)
     * Conventions.getQualifiedAttributeName(ConfigurationClassPostProcessor.class, "configurationClass")
     * 'full'.equals(beanDef.getAttribute(CONFIGURATION_CLASS_ATTRIBUTE));
     *
     */


}

