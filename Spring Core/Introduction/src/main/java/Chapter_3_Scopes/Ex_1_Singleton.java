package Chapter_3_Scopes;

import Chapter_2_First_Spring_App.beans.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Ex_1_Singleton {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        // STEP-1 is this a singleton?
        Developer dev1 = (Developer) context.getBean("scalaDev");
        dev1.setSkill("Delphi");
        System.out.println(dev1.toString());
        Developer dev2 = (Developer) context.getBean("scalaDev");
        System.out.println(dev2.toString());
        // Why do we see skill="Delphi" in dev2 bean?



        // STEP-2 yes, this is a singleton
        Developer dev3 = context.getBean("scalaDev", Developer.class);
        Developer dev4 = context.getBean("javaDev", Developer.class);

        System.out.println(dev1 == dev2);
        System.out.println(dev1 == dev3);
        System.out.println(dev1 == dev4);
    }
}
