package Chapter_2_First_Spring_App.beans;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
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

    public boolean isCoffeeConsumer() {
        return isCoffeeConsumer;
    }

    public void setIsCoffeeConsumer(boolean isCoffeeConsumer) {
        this.isCoffeeConsumer = isCoffeeConsumer;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                ", level=" + level +
                ", isCoffeeConsumer=" + isCoffeeConsumer +
                '}';
    }
}
