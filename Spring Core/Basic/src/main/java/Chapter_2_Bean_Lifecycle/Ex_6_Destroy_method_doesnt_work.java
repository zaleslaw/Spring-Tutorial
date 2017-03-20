package Chapter_2_Bean_Lifecycle;

import beans.Developer;
import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * close() -- Close this application context, destroying all beans in its bean factory.
 * registerShutdownHook() -- Register a shutdown hook with the JVM runtime, closing this context on JVM shutdown unless it has already been closed at that time.
 */
public class Ex_6_Destroy_method_doesnt_work {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("lazyJunior", Developer.class);
        System.out.println(dev.toString());

        /*((AbstractApplicationContext)context).close();*/
        System.out.println(project.toString()); //Object is alive without Spring!
        Thread.sleep(10000);

    }
}
