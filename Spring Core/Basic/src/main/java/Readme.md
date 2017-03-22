0. Add classes

public class Engineer {
    private String name;
    private String skill;
    private int level;
    
public class Manager {
    private String name;
    private int emailPerHour;
    private Set<Engineer> engineers;
    
public class Developer extends Engineer {
    private Map<String, Integer> yearsInFramework;
    private boolean isCoffeeConsumer;

public class AutomationTester extends Engineer {
    private boolean isBro;
    private String favoriteFramework;
    
and generate empty constructors, getters/setters and toString method for each class presented above

1. Define bean with inner map

       <bean id="dev" class="beans.Developer">
              <property name="name" value="Petja"/>
              <property name="skill" value="Java"/>
              <property name="level" value="2"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Spring" value="3"/>
                            <entry key ="Hibernate" value="2"/>
                            <entry key ="Mockito" value="1"/>
                     </map>
              </property>
       </bean>

 Add client code
 
         ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");
         Developer dev = context.getBean("dev", Developer.class);
         System.out.println(dev.toString());
         
 2. To play with inner set, define beans
 
 
        <bean id="tester" class="beans.AutomationTester">
              <property name="name"  value="Vasja"/>
               <property name="skill" value="Python"/>
               <property name="level" value="1"/>
               <property name="favoriteFramework" value="Django"/>
               <property name="isBro" value="true"/>
        </bean>
 
        <bean id="manager" class="beans.Manager">
               <property name="name" value="Ivan Ivanovich"/>
               <property name="emailPerHour" value="1000"/>
               <property name="engineers">
                      <set>
                             <ref bean="dev"/>
                             <ref bean="tester"/>
                      </set>
               </property>
        </bean>
        
        
 add client code
 
         ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");
         Manager man = context.getBean("manager", Manager.class);
         System.out.println(man.toString());
         
3. Sometimes it's not enough and you need to share global List/Map/Set to manipulate with it
To do that, update header of your XML configuration

  xmlns:util="http://www.springframework.org/schema/util"
  
and 
  
  http://www.springframework.org/schema/util
  http://www.springframework.org/schema/util/spring-util-4.2.xsd
  
after that add bean definition for independent collection and bean can be wired by id 'pool'

       <util:set id="pool" set-class="java.util.HashSet">
              <ref bean="dev"/>
              <ref bean="tester"/>
       </util:set>   
       
       <bean id="otherManager" class="beans.Manager">
              <property name="name" value="Sergey Sergeich"/>
              <property name="emailPerHour">
                     <util:constant static-field="java.lang.Math.PI"/>   // also other functionality of util namespace can be used in BD
              </property>
              <property name="engineers">
                     <ref bean="pool"/>
              </property>
       </bean>

add client code

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_1_Beans.xml");

        Set set = context.getBean("pool", Set.class);
        System.out.println(set.toString());

        Manager man = context.getBean("otherManager", Manager.class);
        System.out.println(man.toString());
        

4. **Bean Lifecycle** Sometimes you should check something before bean publication (to check Network connection/File access/Database access)

Copy previous Chapter_1_Beans to Chapter_2_Beans and add class Project


public class Project {
    private String name;
    private List<Engineer> engineers;
    
    + constructor & toString

with init method

    public void init(){
        System.out.println("Check budget");
        System.out.println("Check staging");
    }
    
and bean definition

       <bean id="NY_Times" class="beans.Project" init-method="init">
              <constructor-arg value="Backend for NYT"/>
              <constructor-arg>
                     <list>
                            <ref bean="dev"/>
                            <ref bean="tester"/>
                            <ref bean="manager"/>
                     </list>
              </constructor-arg>
       </bean>
       
also add in Project constructor

System.out.println("I'm gonna be constructor!");

and in Developer empty constructor  => System.out.println("Developer was hired!");

and run client code

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("dev", Developer.class);
        System.out.println(dev.toString());

We can see that init method was called after constructor

5. To configure call afterPropertiesSet() method, add new class ProjectInitBean implementing InitializingBean
and add bean definition

       public class InitBean implements InitializingBean {
           public void afterPropertiesSet() throws Exception {
               System.out.println("After properties set() was called");
           }
       }
       
       public class YetAnotherInitBean implements InitializingBean {
           public void afterPropertiesSet() throws Exception {
               System.out.println("After properties set() from YAIB was called");
           }
       }

       <!--Ex_2_After_Properties_Set-->
       <bean id="InitBean" class="Chapter_2_Bean_Lifecycle.special_beans.InitBean"/>
       <bean id="YetAnotherInitBean" class="Chapter_2_Bean_Lifecycle.special_beans.YetAnotherInitBean"/>

For bean implemented InitializingBean, it will run afterPropertiesSet() after all bean properties have been set

It is recommended that you do not use the InitializingBean interface because it unnecessarily couples the code to Spring

Q: What about the order?
A: Try to move InitBeans bean definitions before Project bean. You will be excited!

6. Add Custom Bean Post Processor with name CustomBPP

<bean class="Chapter_2_Bean_Lifecycle.special_beans.CustomBPP"/>

public class CustomBPP implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " START");
        return null;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " END");
        return null;
    }
}

and run client code: you will get "Invocation of init method failed; nested exception is java.lang.NullPointerException" due to power of BPP
We filtered all beans and return nulls.
Fix it

return null -> return o;

and print out **Object o** before custom init-method call and after

Also we can add AnotherCustomBPP with code 

7. **BPP Ordered Queue**

To have order in BPP queue, implement Ordered interface and method getOrder()

public class AnotherCustomBPP implements BeanPostProcessor, Ordered {
    //before custom init method
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " START 2");
        System.out.println("BPP 2: " + o.toString());
        return o;
    }
    // after custom init method
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " END 2");
        System.out.println("BPP 2: " + o.toString());
        return o;
    }

    public int getOrder() {
        return 2;
    }
}

change first BPP to

public class CustomBPP implements BeanPostProcessor, Ordered {
    //before custom init method
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " START");
        System.out.println("BPP: " + o.toString());
        return o;
    }
    // after custom init method
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " END");
        System.out.println("BPP: " + o.toString());
        return o;
    }

    public int getOrder() {
        return 1;
    }
}


8. Let's create new bean lazyJunior

       <bean id="lazyJunior" class="beans.Developer" lazy-init="true">
              <property name="name" value="Sanja"/>
              <property name="skill" value="Clojure"/>
              <property name="level" value="1"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Spring" value="0"/>
                            <entry key ="Hibernate" value="0"/>
                     </map>
              </property>
       </bean>
       
and run client code 

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("lazyJunior", Developer.class); //changed to other bean
        System.out.println(dev.toString());

9. **Destructor in Spring**

To control object destruction and resources' releasing, maybe defined destroy-method (like init-method)
or maybe created class implementing DisposableBean like that

9.1. public class DestroyBean implements DisposableBean {
       public void destroy() throws Exception {
           System.out.println("Old disposable bean is here!");
       }
    }

and add BD of 

<bean id="DestroyBean" class="Chapter_2_Bean_Lifecycle.special_beans.DestroyBean"/>

This class is related to all beans registered in ApplicationContext.

9.2. also add destroy-method in Project class

    public void destroy() {
        name = name + " closed";
        System.out.println("Go to bench, comrades!");
    }
    

    
and change BD 'NY_Times' adding 'destroy-method="destroy"' to BD header


10. **When does destroy method not work?**

Play with this example

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_2_Beans.xml");

        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

        Developer dev = context.getBean("lazyJunior", Developer.class);
        System.out.println(dev.toString());

        /*((AbstractApplicationContext)context).close();*/
        System.out.println(project.toString()); //Object is alive without Spring!
        Thread.sleep(10000); // kill JVM at this moment

Kill JVM and grab the logs. You will be surprised! But it's obviously!

11. **Bean Inheritance:** add bean definition

       <bean name="engineer" class="beans.Engineer">
              <property name="name" value="anonymous"/>
       </bean>
       
add parent="engineer" to dev, tester, manager beans and remove property 'name' from them
like this:
       
       <bean id="tester" class="beans.AutomationTester" parent="engineer">
              <property name="skill" value="Python"/>
              <property name="level" value="1"/>
              <property name="favoriteFramework" value="Django"/>
              <property name="isBro" value="true"/>
       </bean>
              
run client code

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_3_1_Beans.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());
        
It works cause all three beans have common field 'name'

12. But if we set yet one property in 'engineer' bean like 

       <bean name="engineer" class="beans.Engineer">
              <property name="name" value="anonymous"/>
              <property name="level" value="2"/>
       </bean>

we get "Caused by: org.springframework.beans.NotWritablePropertyException: Invalid property 'level' of bean class [beans.Manager]: Bean property 'level' is not writable or has an invalid setter method"
due to mismatch between property 'level' and setters in manager

Q: Can we resolve this problem?
A: Add setter to 'manager' bean according his bean definition based on parent bean definition.

13. Register Custom BPP and change bean 'NY_Times' removing developer and tester refs.

       <bean id="NY_Times" class="beans.Project">
              <constructor-arg value="Backend for NYT"/>
              <constructor-arg>
                     <list>
                            <ref bean="manager"/> <----- only that man
                     </list>
              </constructor-arg>
       </bean>

enbale for 'dev' and 'tester' lazy initialization

       <bean id="dev" class="beans.Developer" lazy-init="true">
              <property name="name" value="Alex"/>
              <property name="skill" value="Java"/>
              <property name="level" value="2"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Spring" value="3"/>
                            <entry key ="Hibernate" value="2"/>
                            <entry key ="Mockito" value="1"/>
                     </map>
              </property>
       </bean>

       <bean id="tester" class="beans.AutomationTester" lazy-init="true">
              <property name="name" value="Roma"/>
              <property name="skill" value="Python"/>
              <property name="level" value="1"/>
              <property name="favoriteFramework" value="Django"/>
              <property name="isBro" value="true"/>
       </bean>

also delete tham from 'manager' engineer set

       <bean id="manager" class="beans.Manager">
              <property name="emailPerHour" value="1000"/>
              <property name="engineers">
                   <null/> <----- put it here
              </property>
       </bean>

       <bean class="Chapter_2_Bean_Lifecycle.special_beans.CustomBPP"/>

In this case client code and BPP don't print information about 'dev' & 'tester' beans.

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_3_3_Beans.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

To enable this information add tag depends-on in 'manager' bean definition

        <bean id="manager" class="beans.Manager" **depends-on="dev, tester"**>

14. Multiple Context 
Separate all beans on 3 files App.xml; Engineers.xml and Projects.xml

_App.xml_

       <bean id="InitBean" class="Chapter_2_Bean_Lifecycle.special_beans.InitBean"/>
       <bean class="Chapter_2_Bean_Lifecycle.special_beans.CustomBPP"/>
       <bean id="DestroyBean" class="Chapter_2_Bean_Lifecycle.special_beans.DestroyBean"/>
       
_Engineers.xml_

move on here dev, tester, manager and lazyJunior BDs

       <bean id="dev" class="beans.Developer">
              <property name="name" value="Petja"/>
              <property name="skill" value="Java"/>
              <property name="level" value="2"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Spring" value="3"/>
                            <entry key ="Hibernate" value="2"/>
                            <entry key ="Mockito" value="1"/>
                     </map>
              </property>
       </bean>

       <bean id="tester" class="beans.AutomationTester">
             <property name="name"  value="Vasja"/>
              <property name="skill" value="Python"/>
              <property name="level" value="1"/>
              <property name="favoriteFramework" value="Django"/>
              <property name="isBro" value="true"/>
       </bean>

       <bean id="lazyJunior" class="beans.Developer" lazy-init="true">
              <property name="name" value="Sanja"/>
              <property name="skill" value="Clojure"/>
              <property name="level" value="1"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Spring" value="0"/>
                            <entry key ="Hibernate" value="0"/>
                     </map>
              </property>
       </bean>

       <bean id="manager" class="beans.Manager">
              <property name="name">
                            <ref bean="managerName"></ref>

              </property>
              <property name="emailPerHour" value="1000"/>
              <property name="engineers">
                     <set>
                            <ref bean="dev"/>
                            <ref bean="tester"/>
                     </set>
              </property>
       </bean>
       
 

_Projects.xml_ should be filled with NYT bean.

Also add special import commands

<import resource="Engineers.xml"/> to Projects.xml
<import resource="Projects.xml"/> to App.xml

Run client code

        ApplicationContext context = new ClassPathXmlApplicationContext("/Chapter_4/App.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());
 
Q: if we move one bean to another xml file, can we load bean definition correctly?
A: yes, it's like merging in one large file (common context)

For example: extract and move 'managerName' in _Projects.xml_

       <import resource="Engineers.xml"/>

       <bean id="NY_Times" class="beans.Project" init-method="init" destroy-method="destroy">
              <constructor-arg value="Backend for NYT"/>
              <constructor-arg>
                     <list>
                            <ref bean="manager"/>
                     </list>
              </constructor-arg>
       </bean>

       <bean name="managerName" class="java.lang.String">
              <constructor-arg value="Ivan Ivanovich"/>
       </bean>


15. **No wiring**
Add simple client code 

        // Before autowiring
        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_5_1_Beans.xml");
        SmallProject project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        ((AbstractApplicationContext)context).close();
        
 and bean definition in Chapter_5_1_Beans.xml
 
        <bean name="engineer" class="beans.Engineer">
               <property name="name" value="anonymous"/>
        </bean>
 
        <bean id="developer" class="beans.Developer" parent="engineer">
               <property name="skill" value="Java"/>
               <property name="level" value="2"/>
               <property name="yearsInFramework">
                      <map>
                             <entry key="Spring" value="3"/>
                             <entry key ="Hibernate" value="2"/>
                             <entry key ="Mockito" value="1"/>
                      </map>
               </property>
        </bean>
 
        <bean id="tester" class="beans.AutomationTester" parent="engineer">
               <property name="skill" value="Python"/>
               <property name="level" value="1"/>
               <property name="favoriteFramework" value="Django"/>
               <property name="isBro" value="true"/>
        </bean>
 
        <bean id="manager" class="beans.Manager" parent="engineer">
               <property name="emailPerHour" value="1000"/>
               <property name="engineers">
                      <set>
                             <ref bean="developer"/>
                             <ref bean="tester"/>
                      </set>
               </property>
        </bean>
 
        <bean id="Mobile Post of Russian Federation" class="Chapter_5_Autowiring.beans.SmallProject" >
               <constructor-arg value="Android App"/>
               <property name="developer" ref="developer"/>
               <property name="manager" ref="manager"/>
               <property name="tester" ref="tester"/>
        </bean>

Run client code

16. **Autowiring by type**

delete all properties and add autowiring option

       <bean id="Mobile Post of Russian Federation" class="Chapter_5_Autowiring.beans.SmallProject" autowire="byType">
              <constructor-arg value="Autowired by type"/>
       </bean>
       
       
run client code and get profit!       
       

17. **ByType limitations** 

add yet one developer bean

<bean id="dev" class="beans.Developer">
              <property name="skill" value="Scala"/>
              <property name="level" value="3"/>
              <property name="yearsInFramework">
                     <map>
                            <entry key="Akka" value="2"/>
                            <entry key ="Spark" value="1"/>
                            <entry key ="Play" value="2"/>
                     </map>
              </property>
       </bean>
       
and it can be a problem for Spring: No qualifying bean of type [beans.Developer] is defined: expected single matching bean but found 2: developer,dev

18. or other problem: we decided to define managerName bean with String type and miss a few properties (with type String) in engineer bean
and changed global autowiring option in config

<beans **default-autowire="byType"** xmlns="http://www.springframework.org/schema/beans"

       <bean id="Constant" class="java.lang.String">
              <constructor-arg value = "Independent String bean"/>
       </bean>
 
But String is not autowired! Why? Due to documentation of autowire limitations (String, Class, primitives) and due to a lot of errors with usage of early release of Spring

19. How to fix the problem if you have more than 1 instance of specific type? 
Use autowiring by name!

Change global setting 
<beans **default-autowire=~~"byType"~~"byName"** xmlns="http://www.springframework.org/schema/beans"

Add client code

        context = new ClassPathXmlApplicationContext("Chapter_5_3_Beans.xml");
        project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        ((AbstractApplicationContext)context).close();
        
and 'developer' bean will be injected instead of mismatch with autowiring by type

20. Sometimes, you need exclude bean from autowiring processes due to couple of reasons

add special tag autowire-candidate=false

For example to 'manager' bean:

Attention: but it doesn't work for byName regime from version to version
https://jira.spring.io/browse/SPR-15072

But it works for wiring by Type

change global setting again to check
<beans **default-autowire="byType"~~"byName"~~**


WTF?

Don't use autowiring at all