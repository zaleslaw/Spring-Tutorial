package beans;

import java.util.Set;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Manager {
    private String name;
    private int emailPerHour;
    private Set<Developer> developers;

    public Manager() {
    }

    public Manager(String name, int emailPerHour, Set<Developer> developers) {
        this.name = name;
        this.emailPerHour = emailPerHour;
        this.developers = developers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmailPerHour() {
        return emailPerHour;
    }

    public void setEmailPerHour(int emailPerHour) {
        this.emailPerHour = emailPerHour;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", emailPerHour=" + emailPerHour +
                ", developers=" + developers +
                '}';
    }
}
