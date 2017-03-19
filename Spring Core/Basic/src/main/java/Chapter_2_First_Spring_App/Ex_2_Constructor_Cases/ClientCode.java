package Chapter_2_First_Spring_App.Ex_2_Constructor_Cases;

import Chapter_2_First_Spring_App.beans.Developer;
import Chapter_2_First_Spring_App.beans.Project;
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


        // Step-1: DI based on constructor with full list of parameters
        Developer javaDev = context.getBean("javaDev", Developer.class);
        System.out.println(javaDev.toString());

        // Step-2: DI based on constructor with one parameter
        Developer intern = context.getBean("intern", Developer.class);
        intern.setLevel(1);
        intern.setSkill("Kotlin");
        System.out.println(intern.toString());

        // Step-3: DI based on constructor with parameter order
        Developer dotNetDev = context.getBean("dotNetDev", Developer.class);
        System.out.println(dotNetDev.toString());

        // Step-4: When you need to write down type of parameter
        Developer anonymous = context.getBean("anonymous", Developer.class);
        System.out.println(anonymous.toString());

        // Step-5: Constructor with reference parameters
        //Project taxiBumer = context.getBean("newProject", Project.class);
        Project taxiBumer = context.getBean("taxiUvezet", Project.class);
        System.out.println(taxiBumer.getTeamLead().toString());
    }
}
