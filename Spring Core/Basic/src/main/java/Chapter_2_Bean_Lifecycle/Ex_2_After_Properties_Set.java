package Chapter_2_Bean_Lifecycle;

import beans.Developer;
import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The afterPropertiesSet or @PostConstruct annotated method is called
 * after an instance of class is created and all the properties have been set.
 * For instance if you would like to preload some data that can be done in this method as all the dependencies have been set.
 * If you only have mandatory dependencies you might be better of using constructor injection
 * and instead of using InitializingBean or @PostConstruct put the initializing logic in the constructor.
 * This will only work if all the dependencies are injected through the constructor,
 * if you have optional dependencies set by set methods then you have no choice but to use @PostConstruct or InitializingBean.
 *
 * It is recommended that you do not use the InitializingBean interface because it unnecessarily couples the code to Spring
 *
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-lifecycle-combined-effects
 */
public class Ex_2_After_Properties_Set {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("dev", Developer.class);
        System.out.println(dev.toString());
    }
}
