package Chapter_3_Scopes;

import Chapter_2_First_Spring_App.beans.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Let's experiment with two contexts
 * Is it a global singleton across Spring container or context-local?
 */
public class Ex_2_Two_Singletons {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev1 = (Developer) context.getBean("javaDev");
        Developer dev2 = (Developer) context.getBean("javaDev");

        ApplicationContext otherContext = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev3 = otherContext.getBean("javaDev", Developer.class);
        System.out.println(dev1 == dev2);
        System.out.println(dev1 == dev3);

        // Yes, context-local

    }
}
