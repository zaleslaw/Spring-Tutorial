package beans;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Engineer {
    private String name;
    private String skill;
    private int level;

    public Engineer() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "Engineer{" +
                "name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                ", level=" + level +
                '}';
    }
}
