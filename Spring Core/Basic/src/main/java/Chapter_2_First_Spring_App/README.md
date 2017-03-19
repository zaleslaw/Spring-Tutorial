1. Add dependency

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.2.5.RELEASE</version>
    </dependency>

2. Define XML-configuration in "resource" folder with name "Beans.xml"

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
</beans>


3. Add class Developer

public class Developer {
    private String name;
    private String skill;
    private int level;
    private boolean isCoffeeConsumer;

    public Developer(String name, String skill, int level, boolean isCoffeeConsumer) {
        this.name = name;
        this.skill = skill;
        this.level = level;
        this.isCoffeeConsumer = isCoffeeConsumer;
    }

    public Developer(String name) {
        this.name = name;
    }

    public Developer() {
    }

    + getters/setters/toString

4. Add client code to get dependency from Application context and run it

public class ClientCode {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev = (Developer) context.getBean("dev");
        System.out.println(dev.toString());
    }
}


5. Get org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'dev' is defined
and add bean definition in Beans.xml

<bean id="dev" class="Chapter_2_First_Spring_App.beans.Developer"/>

6. Re-run ClientCode. PROFIT!!!

7. Add two new bean definition

