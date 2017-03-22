package Chapter_2_Bean_Lifecycle;

import beans.Developer;
import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Internally, the Spring Framework uses BeanPostProcessor implementations to process any callback interfaces it can find and call the appropriate methods.
 * If you need custom features or other lifecycle behavior Spring does not offer out-of-the-box, you can implement a BeanPostProcessor yourself.
 */
public class Ex_1_Init_method {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("dev", Developer.class);
        System.out.println(dev.toString());

    }
}
