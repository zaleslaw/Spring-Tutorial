package Chapter_3_Scopes;

import Chapter_2_First_Spring_App.beans.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_3_Prototype {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");


        Developer dev1 = (Developer) context.getBean("scalaDev");
        dev1.setSkill("Delphi");
        System.out.println(dev1.toString());
        Developer dev2 = (Developer) context.getBean("scalaDev");
        System.out.println(dev2.toString());


        // STEP-2 yes, this is not a singleton
        System.out.println(dev1 == dev2);

    }
}
