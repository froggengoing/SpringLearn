package Context;

import com.froggengo.springlearn.SpringLearnMain;
import org.junit.Test;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.Conventions;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class TestContext {

    @Test
    public  void beanUtil() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> aClass = ClassUtils.forName("Context.Book",Thread.currentThread().getContextClassLoader());
        //选构造函数初始化
        Constructor<?> constructor = aClass.getConstructor(new Class<?>[]{String.class, String.class});
        Book book = (Book) BeanUtils.instantiateClass(constructor, "特立独行的猪", "王小波");
        System.out.println(book.getAuth()+"--"+book.getName());
        //默认构造函数无参初始化
        Book book2 = (Book) BeanUtils.instantiateClass(aClass);
        book2.setAuth("黄金时代");
        System.out.println(book2.getAuth());
        // BeanUtils.instantiateClass()
    }
    @Test
    public void testAnnotation(){
        /** {@link BeanDefinitionLoader.isComponent()}*/
        System.out.println(AnnotationUtils.findAnnotation(SpringLearnMain.class, Component.class).toString());
        Annotation[] annotations = SpringLearnMain.class.getAnnotations();
        Arrays.stream(annotations).forEach(n-> System.out.println("annotation:"+n));
        Annotation[] declaredAnnotations = SpringLearnMain.class.getDeclaredAnnotations();
        Arrays.stream(declaredAnnotations).forEach(n-> System.out.println( "decalreAnnotation:"+n));
       // 注意获取注解的注解是：注解.annotationType().getDeclaredAnnotations()
        Arrays.stream(annotations[0].annotationType().getDeclaredAnnotations()).forEach(n-> System.out.println("parentAnnotation:"+n));
        //System.out.println(SpringLearnMain.class.getAnnotation(SpringBootApplication.class).toString());
    }
    @Test
    public void testBeanDefinition(){
        /**{@link org.springframework.context.annotation.AnnotatedBeanDefinitionReader.registerBean(java.lang.Class<?>, java.lang.String, java.lang.Class<? extends java.lang.annotation.Annotation>...)}*/
        //1、获得注解BenDefinition
        System.out.println("1、获得注解BenDefinition");
        AnnotatedGenericBeanDefinition definition = new AnnotatedGenericBeanDefinition(Book.class);
        //注解
        AnnotationMetadata metadata = definition.getMetadata();
        metadata.getAnnotationTypes().forEach(n-> System.out.println("  注解："+n));
        //beanclass
        System.out.println("  类名："+definition.getBeanClass());
        //2、解析类上声明的注解,
        // @Lazy
        //@Description("测试 AnnotationConfigUtils")
        //@DependsOn({"com.froggengo.springlearn.SpringLearnMain"})
        //@Role(BeanDefinition.ROLE_APPLICATION)
        AnnotationConfigUtils.processCommonDefinitionAnnotations(definition);
        System.out.println("2、解析注解后");
        Arrays.stream(definition.getDependsOn()).forEach(n-> System.out.println("  依赖："+n) );
        System.out.println("  是否延迟初始化："+definition.isLazyInit());
        System.out.println("  角色等级role：参考{BeanDefinition.ROLE_APPLICATION}："+definition.getRole());
        System.out.println("  描述："+definition.getDescription());
        //3、解析scope(指定范围及代理模式ScopedProxyMode 、ConfigurableBeanFactory.SCOPE_PROTOTYPE)，导致打印日志
        System.out.println("3、获取BeanClass的@Scope的代理模式，默认NO");
        /*	value
            singleton	表示该bean是单例的。(默认)
            prototype	表示该bean是多例的，即每次使用该bean时都会新建一个对象。
            request		在一次http请求中，一个bean对应一个实例。
            session		在一个httpSession中，一个bean对应一个实例。

        proxyMode
            DEFAULT			不使用代理。(默认)
            NO				不使用代理，等价于DEFAULT。
            INTERFACES		使用基于接口的代理(jdk dynamic proxy)。
            TARGET_CLASS	使用基于类的代理(cglib)。*/
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotationScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
        ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(definition);
        definition.setScope(scopeMetadata.getScopeName());
        System.out.println("  scope："+definition.getScope());
        //4、获取BeanDefinitionHolder
        System.out.println("4、生成beanname，并生成BeanDefinitionHolder");
        AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
        String beanName = beanNameGenerator.generateBeanName(definition, beanFactory);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(definition,beanName);
        System.out.println("  beanName："+definitionHolder.getBeanName());
        System.out.println("  BeanLongDescription: "+definitionHolder.getLongDescription());
        System.out.println("  BeanShortDescription: "+definitionHolder.getShortDescription());
        //5、scope非NO时，生成代理definition，如为target_class 时，使用基于类的代理(cglib)
        BeanDefinitionHolder scopeDefinitionHolder = ScopedProxyUtils.createScopedProxy(definitionHolder, beanFactory, true);
        beanFactory.registerBeanDefinition(scopeDefinitionHolder.getBeanName(),scopeDefinitionHolder.getBeanDefinition());
        //最后将在beanfactory中beanDefinitionMap记录代理的definition和原理的definition ，不太懂
        //如果为no则beanfactory中beanDefinitionMap直接记录definition
        //ScopedProxyFactoryBean
    }

    /**
     *
     * refreshContext时执行AbstractApplicationContext#invokeBeanFactoryPostProcessors，会有ConfigurationClassPostProcessor
     * 判断：要取出@configuration注解及类似的类，通过下面的方式判断是否，被注释。循环找注解及注解的
     * 处理：ConfigurationClassParser ：new ConfigurationClassParser(this.metadataReaderFactory, this.problemReporter, this.environment,
     *                                                              this.resourceLoader, this.componentScanBeanNameGenerator, registry);
     */
    @Test
    public void testMainClassDefine() throws IOException {
        //AnnotatedGenericBeanDefinition definition = new AnnotatedGenericBeanDefinition(SpringLearnMain.class);
        ConcurrentReferenceCachingMetadataReaderFactory metadataReaderFactory = new ConcurrentReferenceCachingMetadataReaderFactory();
        ClassPathResource resource = new ClassPathResource("com\\froggengo\\springlearn\\SpringLearnMain.class");
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
        boolean isConfiguration= ((AnnotationMetadata) metadataReader.getClassMetadata()).isAnnotated(Configuration.class.getName());
        System.out.println(isConfiguration);

/*        RootBeanDefinition definition1 = new RootBeanDefinition(
                SharedMetadataReaderFactoryContextInitializer.SharedMetadataReaderFactoryBean.class);
        registry.registerBeanDefinition(BEAN_NAME, definition);*/

    }


}
