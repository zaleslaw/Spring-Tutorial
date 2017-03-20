package Chapter_1_Collection_Injection;

import beans.Manager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_3_Util_Namespace {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");

        Set set = context.getBean("pool", Set.class);
        System.out.println(set.toString());


        // [PI] letters per hour
        Manager man = context.getBean("otherManager", Manager.class);
        System.out.println(man.toString());
    }
}
