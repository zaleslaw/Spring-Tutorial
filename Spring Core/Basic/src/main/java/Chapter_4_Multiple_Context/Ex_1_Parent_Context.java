package Chapter_4_Multiple_Context;

import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_1_Parent_Context {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_4/App.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());
    }
}
