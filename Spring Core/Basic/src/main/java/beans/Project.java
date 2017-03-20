package beans;

import java.util.List;

/**
 * Created by Alexey_Zinovyev on 20-Mar-17.
 */
public class Project {
    private String name;
    private List<Object> engineers;

    public Project(String name, List<Object> engineers) {
        this.name = name;
        this.engineers = engineers;
        System.out.println("I'm gonna be constructor!");
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", engineers=" + engineers +
                '}';
    }

    public void init(){
        name = name + " ++";
        System.out.println("Check budget");
        System.out.println("Check staging");
    }

    public void destroy() {
        name = name + " closed";
        System.out.println("Go to bench, comrades!");
    }


    // you can't pass parameter and Spring can't find this method due to its signature
    // Couldn't find an init method named 'init' on bean with name 'NY_Times'
/*    public void init(String parameter){
        System.out.println("Check budget");
        System.out.println("Check staging");
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("I'm gonna be setter!");
    }

    public List<Object> getEngineers() {
        return engineers;
    }

    public void setEngineers(List<Object> engineers) {
        this.engineers = engineers;
    }
}
