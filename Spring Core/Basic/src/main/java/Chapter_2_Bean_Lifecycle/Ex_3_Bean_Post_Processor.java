package Chapter_2_Bean_Lifecycle;

import beans.Developer;
import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * By default, ApplicationContext implementations eagerly create and configure all singleton beans as part of the initialization process
 * A lazy-initialized bean tells the IoC container to create a bean instance when it is first requested, rather than at startup.
 */
public class Ex_3_Bean_Post_Processor {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("dev", Developer.class);
        System.out.println(dev.toString());
    }
}
