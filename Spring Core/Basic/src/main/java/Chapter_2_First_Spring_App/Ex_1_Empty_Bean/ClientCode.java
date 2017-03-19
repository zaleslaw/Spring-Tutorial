package Chapter_2_First_Spring_App.Ex_1_Empty_Bean;

import Chapter_2_First_Spring_App.beans.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class ClientCode {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev = (Developer) context.getBean("dev");
        System.out.println(dev.toString());
    }
}
