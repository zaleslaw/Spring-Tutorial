package Chapter_5_Autowiring;

import Chapter_5_Autowiring.beans.SmallProject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_1_Autowiring {
    public static void main(String[] args) {

        // Before autowiring
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_5_1_Beans.xml");
        SmallProject project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        ((AbstractApplicationContext)context).close();

        // Autowiring by type
        context = new ClassPathXmlApplicationContext("Chapter_5_2_Beans.xml");
        project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        String str = context.getBean("Constant", String.class);
        System.out.println(str);

        ((AbstractApplicationContext)context).close();

        // Autowiring by Name

        context = new ClassPathXmlApplicationContext("Chapter_5_3_Beans.xml");
        project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        ((AbstractApplicationContext)context).close();
    }
}
