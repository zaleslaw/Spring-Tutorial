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
