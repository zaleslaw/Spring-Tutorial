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

    public Developer(int level) {
        this.level = level;
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

// CONSTRUCTORS

7. Add new bean definition
       <bean id="javaDev" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg value="Alex"/>
              <constructor-arg value="Java"/>
              <constructor-arg value="3"/>
              <constructor-arg value="true"/>
       </bean>

and to client code

Developer javaDev = context.getBean("javaDev", Developer.class);
System.out.println(javaDev.toString());

It works without type mention in Beans.xml

8. Add new bean definition

<bean id="intern" class="Chapter_2_First_Spring_App.beans.Developer">
    <constructor-arg value="Roman"/>
</bean>

add lines to client code

        Developer intern = context.getBean("intern", Developer.class);
        intern.setLevel(1);
        intern.setSkill("Kotlin");
        System.out.println(intern.toString());

9. Add new bean definition

       <bean id="dotNetDev" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg value="DotNet"/>
              <constructor-arg value="Peter"/>
              <constructor-arg value="2"/>
              <constructor-arg value="false"/>
       </bean>

add lines to client code

Developer dotNetDev = context.getBean("dotNetDev", Developer.class);
System.out.println(dotNetDev.toString());

But it doesn't work correctly due to one type for two first parameters

Fix the problem with parameter order, for example

       <bean id="dotNetDev" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg index="1" value="DotNet"/>
              <constructor-arg index="0" value="Peter"/>
              <constructor-arg value="2"/>
              <constructor-arg value="false"/>
       </bean>


10. If you want to configure through constructor setting only skill, you can be wondered

add bean definition

       <bean id="anonymous" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg value="15"/>
       </bean>

add lines to client code

        Developer anonymous = context.getBean("anonymous", Developer.class);
        System.out.println(anonymous.toString());

you can fix the problem to fill the type property for constructor parameter like this

       <bean id="anonymous" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg type="int" value="15"/>
       </bean>

But what about reference parameters?

11. Add class Project
public class Project {
    private String name;
    private Developer teamLead;

    public Project(String name, Developer teamLead) {
        this.name = name;
        this.teamLead = teamLead;
    }

    + getters, setters, toString
}


12. Add client code

        Project taxiBumer = context.getBean("taxiBumer", Project.class);
        System.out.println(taxiBumer.getTeamLead().toString());

and bean definition

       <bean id="newProject" name="taxiBumer taxiBiber, taxiNett; taxiUvezet" class="Chapter_2_First_Spring_App.beans.Project">
              <constructor-arg value="taxiBumer"/>
              <constructor-arg ref="javaDev"/>
       </bean>

you can change on         Project taxiBumer = context.getBean("taxiUvezet", Project.class);

// SETTERS

13. Add new bean definitions

      <bean id="Java 10" name="Maybe later" class="Chapter_2_First_Spring_App.beans.Project">
              <property name="name" value="Java 10. Early Release"/>
              <property name="teamLead" ref="teamLead"/>
       </bean>

But you see "no matching constructor in Project" due to non-existence of default constructor

Let's add it

    public Project() {
    }

and also add yet one field to Project

  private Developer teamLeadHelper;

after that add two bean definitions

       <bean name="teamLead" class="Chapter_2_First_Spring_App.beans.Developer">
              <property name="name" value="Doug Lea"/>
              <property name="skill" value="Java"/>
              <property name="level" value="100"/>
              <property name="isCoffeeConsumer" value="true"/>
       </bean>

       <bean id="senior" name="workingHorse" class="Chapter_2_First_Spring_App.beans.Developer">
              <constructor-arg value="Aleksey Shipilev"/>
              <property name="skill" value="Java"/>
              <property name="level" value="10"/>
              <property name="isCoffeeConsumer" value="true"/>
       </bean>

and assign Alexey to Java 10 team with adding yet one property

 <property name="teamLeadHelper" ref="workingHorse"/> //Attention! It works for both: id and name

But it doesn't work. Why?

Because we have no getter for this field (Spring doesn't know about private fields, it discovers them through getters and setters

Ok, add setter for teamLeadHelper field and regenerate toString method

And finish with small client code

public class ClientCode {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Project project = context.getBean("Java 10", Project.class);
        System.out.println(project.toString());
    }
}

14. Let's add new project with CEO presented in inner bean

       <bean id="cool_startup" class="Chapter_2_First_Spring_App.beans.Project">
              <property name="name" value="pied piper"/>
              <property name="teamLead">
                     <bean class="Chapter_2_First_Spring_App.beans.Developer">
                            <constructor-arg value="Richard Hendricks"/>
                            <property name="isCoffeeConsumer" value="true"/>
                     </bean>
              </property>
       </bean>

and client code

        Project piper = context.getBean("cool_startup", Project.class);
        System.out.println(piper.toString());


Q: What will be printed if we add after <constructor-arg value="Richard Hendricks"/> next row <property name="name" value="Richi"/> - Richi or Richard Hendricks?

A: "Richi" cause of setter will be called later than constructor

15. Or play with alias

<alias name="cool_startup" alias="piper"/>

Project piper2 = context.getBean("piper", Project.class);

Let's check, that we have one object
System.out.println(piper==piper2);

Result: true

// SCOPES

16.  Make two instances of one bean

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev1 = (Developer) context.getBean("scalaDev");

set other skill in first bean and print out both of them

        dev1.setSkill("Delphi");
        System.out.println(dev1.toString());
        Developer dev2 = (Developer) context.getBean("scalaDev");
        System.out.println(dev2.toString());

add bean definition

       <bean id="scalaDev" class="Chapter_2_First_Spring_App.beans.Developer" >
              <constructor-arg value="Martin Odersky"/>
              <constructor-arg value="Scala"/>
              <constructor-arg value="10"/>
              <constructor-arg value="false"/>
       </bean>

What's strange behaviour?
It's like a singleton. Check it!

        Developer dev3 = context.getBean("scalaDev", Developer.class);
        Developer dev4 = context.getBean("javaDev", Developer.class);

        System.out.println(dev1 == dev2);
        System.out.println(dev1 == dev3);
        System.out.println(dev1 == dev4);

17. What about two contexts?

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev1 = (Developer) context.getBean("javaDev");
        Developer dev2 = (Developer) context.getBean("javaDev");

        ApplicationContext otherContext = new ClassPathXmlApplicationContext("Beans.xml");
        Developer dev3 = otherContext.getBean("javaDev", Developer.class);
        System.out.println(dev1 == dev2);
        System.out.println(dev1 == dev3);

 true true or true false? (of course true false)


 18. But how break singleton object creation? It's easy! Change scope.

 <bean id="scalaDev" class="Chapter_2_First_Spring_App.beans.Developer" > => <bean id="scalaDev" class="Chapter_2_First_Spring_App.beans.Developer" scope="prototype">

 and check

        Developer dev1 = (Developer) context.getBean("scalaDev");
        dev1.setSkill("Delphi");
        System.out.println(dev1.toString());
        Developer dev2 = (Developer) context.getBean("scalaDev");
        System.out.println(dev2.toString());

        System.out.println(dev1 == dev2);

 result: false