package Chapter_2_First_Spring_App.beans;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Project {
    private String name;
    private Developer teamLead;
    private Developer teamLeadHelper;

    public Project(String name, Developer teamLead) {
        this.name = name;
        this.teamLead = teamLead;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Developer getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(Developer teamLead) {
        this.teamLead = teamLead;
    }

    public void setTeamLeadHelper(Developer teamLeadHelper) {
        this.teamLeadHelper = teamLeadHelper;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", teamLead=" + teamLead +
                ", teamLeadHelper=" + teamLeadHelper +
                '}';
    }
}
