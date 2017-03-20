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
         
 2. To play with inner set define beans
 
 
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
  http://www.springframework.org/schema/util/spring-util-3.0.xsd
  
after that add bean definition for independent collection and you can wire this bean with other by id

       <util:set id="pool" set-class="java.util.HashSet">
              <ref bean="dev"/>
              <ref bean="tester"/>
       </util:set>   
       
       <bean id="otherManager" class="beans.Manager">
              <property name="name" value="Sergey Sergeich"/>
              <property name="emailPerHour">
                     <util:constant static-field="java.lang.Math.PI"/>
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
        
**Bean Lifecycle**
4. Sometimes you should check something before bean publication (to check Network connection/File access/Database access)

add class Project
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

and run client code

We can see that init method was called after constructor

5. To configure call afterPropertiesSet() method, add new class ProjectInitBean implementing InitializingBean
and add bean definition

       <!--Ex_2_After_Properties_Set-->
       <bean id="InitBean" class="Chapter_2_Bean_Lifecycle.special_beans.InitBean"/>
       <bean id="YetAnotherInitBean" class="Chapter_2_Bean_Lifecycle.special_beans.YetAnotherInitBean"/>

For bean implemented InitializingBean, it will run afterPropertiesSet() after all bean properties have been set

It is recommended that you do not use the InitializingBean interface because it unnecessarily couples the code to Spring

Q: What about the order?
A: Try to move InitBeans bean definitions before Project bean. You will be excited!

6. Add Custom Bean Post Processor with name CustomBPP

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

7. Let's create new bean lazyJunior

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

8. Destroy metod TODO:
9. **Bean Inheritance:** add bean definition

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

But if we set yet one property in 'engineer' bean like 

       <bean name="engineer" class="beans.Engineer">
              <property name="name" value="anonymous"/>
              <property name="level" value="2"/>
       </bean>

we get "Caused by: org.springframework.beans.NotWritablePropertyException: Invalid property 'level' of bean class [beans.Manager]: Bean property 'level' is not writable or has an invalid setter method"
due to mismatch between property 'level' and setters in manager

Q: Can we resolve this problem?
A: Add setter to 'manager' bean according his bean definition based on parent bean definition.

10.Register Custom BPP and make bean 'pool' with 'manager' bean without developer and tester.

       <bean id="NY_Times" class="beans.Project">
              <constructor-arg value="Backend for NYT"/>
              <constructor-arg>
                     <list>
                            <ref bean="manager"/>
                     </list>
              </constructor-arg>
       </bean>

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

       <bean id="manager" class="beans.Manager">
              <property name="emailPerHour" value="1000"/>
              <property name="engineers">
                   <null/>
              </property>
       </bean>

       <bean class="Chapter_2_Bean_Lifecycle.special_beans.CustomBPP"/>

In this case client code and BPP don't print information about 'dev' & 'tester' beans.

        ApplicationContext context = new ClassPathXmlApplicationContext("Chapter_3_3_Beans.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());

To enable this information add tag depends-on in 'manager' bean definition
 <bean id="manager" class="beans.Manager" **depends-on="dev, tester"**>

11. Multiple Context 
Separate all beans on 3 files App.xml; Engineers.xml and Projects.xml

App.xml

       <bean id="InitBean" class="Chapter_2_Bean_Lifecycle.special_beans.InitBean"/>
       <bean class="Chapter_2_Bean_Lifecycle.special_beans.CustomBPP"/>
       <bean id="DestroyBean" class="Chapter_2_Bean_Lifecycle.special_beans.DestroyBean"/>
       
Engineers.xml

move here dev, tester, manager and lazyJunior

Projects.xml should be filled with NYT bean.

Also add special import commands

<import resource="Engineers.xml"/> to Projects.xml
<import resource="Projects.xml"/> to App.xml

Run client code

        ApplicationContext context = new ClassPathXmlApplicationContext("/Chapter_4/App.xml");
        Project project = context.getBean("NY_Times", Project.class);
        System.out.println(project.toString());
 
Q: if we move one bean to another xml file, can we load bean definition correctly?
A: yes, it's like merging in one large file (common context)

For example: extract and move 'managerName'


// AUTOWIRING

12. **No wiring**
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

13. **ByType**

delete all properties and add  autowiring option

       <bean id="Mobile Post of Russian Federation" class="Chapter_5_Autowiring.beans.SmallProject" autowire="byType">
              <constructor-arg value="Autowired by type"/>
       </bean>
       
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

14. or other problem: we decided to define managerName bean with String type and miss a few properties (with type String) in engineer bean
and changed global autowiring option in config

<beans **default-autowire="byType"** xmlns="http://www.springframework.org/schema/beans"

       <bean id="Constant" class="java.lang.String">
              <constructor-arg value = "Independent String bean"/>
       </bean>
 
But String is not autowired! Why? Due to documentation of autowire limitations (String, Class, primitives) and due to a lot of errors with usage of early release of Spring

15. How to fix the problem if you have more that 1 instances of specific type? Use autowiring by name!

Add client code

        context = new ClassPathXmlApplicationContext("Chapter_5_3_Beans.xml");
        project = context.getBean("Mobile Post of Russian Federation", SmallProject.class);
        System.out.println(project.toString());

        ((AbstractApplicationContext)context).close();
        
and 'developer' bean will be injected instead of mismatch with autowiring by type

16. Sometimes, you need exclude bean from autowiring processes due to couple of reasons

add special tag autowire-candidate=false

For example to 'manager' bean:

Attention: but it doesn't work for byName regime from version to version
https://jira.spring.io/browse/SPR-15072

But it works for wiring by Type

WTF?