package Chapter_5_Autowiring.beans;

import beans.AutomationTester;
import beans.Developer;
import beans.Manager;

/**
 * Created by Alexey_Zinovyev on 20-Mar-17.
 */
public class SmallProject {
    private String name;
    private Developer developer;
    private AutomationTester tester;
    private Manager manager;

    public SmallProject(String name) {
        this.name = name;
    }

    public SmallProject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public AutomationTester getTester() {
        return tester;
    }

    public void setTester(AutomationTester tester) {
        this.tester = tester;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "SmallProject{" +
                "name='" + name + '\'' +
                ", developer=" + developer +
                ", tester=" + tester +
                ", manager=" + manager +
                '}';
    }
}
