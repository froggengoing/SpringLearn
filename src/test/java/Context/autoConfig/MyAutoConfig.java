package Context.autoConfig;


import com.froggengo.springlearn.SpringLearnMain;
import org.junit.Test;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.stream.Stream;

import static java.lang.Class.*;

public class MyAutoConfig {

    /**
     * CASE1:通过AutoConfigurationImportSelector查找本项目的自动配置类
     */
    @Test
    public void testImportSelector()   {
        AutoConfigurationImportSelector importSelector = new AutoConfigurationImportSelector();
        StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(SpringLearnMain.class,false);
        StandardServletEnvironment environment = new StandardServletEnvironment();
        importSelector.setEnvironment(environment);//否则selectImports报错
        annotationMetadata.getAnnotationTypes().forEach(n-> System.out.println("注解"+n));
        Stream<String> stream = Arrays.stream(importSelector.selectImports(annotationMetadata));
        stream.forEach(n->System.out.println("自动配置类："+n));

    }
    /**
     *获取appClassLoader下的自动配置文件spring-autoconfigure-metadata.properties路径
     */
    @Test
    public void  testGetResource() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String autoConfigure = "META-INF/spring-autoconfigure-metadata.properties";
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        System.out.println(classLoader);
        Enumeration<URL> resources = classLoader.getResources(autoConfigure);//注意getResource是返回第一个
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println("获取url--"+url);
        }
        UrlResource urlResource = new UrlResource("jar:file:/D:/apache-maven-3.5.3/repository2/org/springframework/boot/spring-boot-autoconfigure/2.0.5.RELEASE/spring-boot-autoconfigure-2.0.5.RELEASE.jar!/META-INF/spring-autoconfigure-metadata.properties");
        Properties properties = new Properties();
        /*spring写法*/
        Properties loadProperties = PropertiesLoaderUtils.loadProperties(urlResource);
        properties.putAll(loadProperties);
        properties.entrySet().stream().forEach(n-> System.out.println("属性值："+n));
        /*       自己写法
            其实spring底层还是调用properties.load(),只不过还封装了loadXml()
            properties.load(urlResource.getInputStream());
            properties.entrySet().stream().forEach(n-> System.out.println("属性值："+n));
        */
    }
    /**
     * 通过反射将META-INF/spring-autoconfigure-metadata.properties封装在AnconfigurationMetadata中
     * spring-autoconfigure-metadata.properties主要是配置自动配置类的ConditionOnClass、AutoConfigureBefore、AutoConfigureAfter等属性
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     */
    @Test
    public void testReflect() throws IOException, ClassNotFoundException {
        UrlResource urlResource = new UrlResource("jar:file:/D:/apache-maven-3.5.3/repository2/org/springframework/boot/spring-boot-autoconfigure/2.0.5.RELEASE/spring-boot-autoconfigure-2.0.5.RELEASE.jar!/META-INF/spring-autoconfigure-metadata.properties");
        Properties properties = new Properties();
        properties.load(urlResource.getInputStream());
        UrlResource jarResource = new UrlResource("file:D:\\apache-maven-3.5.3\\repository2\\org\\springframework\\boot\\spring-boot-autoconfigure\\2.0.5.RELEASE\\spring-boot-autoconfigure-2.0.5.RELEASE.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarResource.getURL()});
        Class<?> aClass = urlClassLoader.loadClass("org.springframework.boot.autoconfigure.AutoConfigurationMetadataLoader");
        Class<?>[] declaredClasses = aClass.getDeclaredClasses();
        Arrays.stream(declaredClasses).forEach(n->{
            System.out.println("声明类:"+n.getName());
            System.out.println(n.getCanonicalName());
            System.out.println(n.getSimpleName());
            System.out.println(n.getTypeName());
            if("PropertiesAutoConfigurationMetadata".equals(n.getSimpleName())){
                try{
                    //getDeclaredMethods当前类声明的方法不包括父类包括私有，getMethod所有public方法包括父类
                    //Arrays.stream(n.getDeclaredMethods()).forEach(s-> System.out.println("方法："+s));
                    Constructor<?> constructor = n.getDeclaredConstructor(Properties.class);//必须是getDeclaredConstructor才能获取私有
                    constructor.setAccessible(true);//私有类必须手动设置访问权限
                    AutoConfigurationMetadata configurationMetadata = (AutoConfigurationMetadata)constructor.newInstance(properties);
                    String conditionalStr = configurationMetadata.get("org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration",
                            "ConditionalOnClass");
                    System.out.println("封装成PropertiesAutoConfigurationMetadata："+conditionalStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取spring.factories的 EnableAutoConfiguration配置的类
     */
    /**
     * 匹配spring.factories的 EnableAutoConfiguration配置的类与spring-autoconfigure-metadata.properties中对应配置的ConditionOnClass是否存在
     */
    /**
     * 判断OnClassCondition的类是否有
     * classLoader.loadClass(className)或 Class.forName(className)是否报错
     */


}
