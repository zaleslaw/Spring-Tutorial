package Chapter_1_Collection_Injection;

import beans.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_1_Map_Injection {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");
        Developer dev = context.getBean("dev", Developer.class);
        System.out.println(dev.toString());
    }
}
