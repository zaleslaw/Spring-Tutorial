package Chapter_2_First_Spring_App.Ex_3_Setter_Cases;

import Chapter_2_First_Spring_App.beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class ClientCode {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Project project = context.getBean("Java 10", Project.class);
        System.out.println(project.toString());

        Project piper = context.getBean("cool_startup", Project.class);
        System.out.println(piper.toString());

        Project piper2 = context.getBean("piper", Project.class);
        System.out.println(piper.toString());

        System.out.println(piper==piper2);
    }
}
