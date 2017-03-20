package beans;

import java.util.Set;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Manager {
    private String name;
    private int emailPerHour;
    private Set<Engineer> engineers;

    public Manager() {
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

    public Set<Engineer> getEngineers() {
        return engineers;
    }

    public void setEngineers(Set<Engineer> engineers) {
        this.engineers = engineers;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                ", emailPerHour=" + emailPerHour +
                ", engineers=" + engineers +
                '}';
    }
}
