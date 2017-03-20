package Chapter_3_Bean_Dependencies;

import beans.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * If a bean is a dependency of another that usually means that one bean is set as a property of another.
 * Typically you accomplish this with the <ref/> element in XML-based configuration metadata.
 *
 * However, sometimes dependencies between beans are less direct;
 * for example, a static initializer in a class needs to be triggered, such as database driver registration.
 * The depends-on attribute can explicitly force one or more beans to be initialized
 * before the bean using this element is initialized.
 */
public class Ex_3_Depends_on {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_3_3_Beans.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());
    }
}
