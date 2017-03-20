package Chapter_2_Bean_Lifecycle;

import beans.Developer;
import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_4_Lazy_initialization {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("lazyJunior", Developer.class);
        System.out.println(dev.toString());
    }
}
