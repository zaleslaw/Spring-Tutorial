package Chapter_1_Collection_Injection;

import beans.Manager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_2_Set_Injection {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");
        Manager man = context.getBean("manager", Manager.class);
        System.out.println(man.toString());
    }
}
