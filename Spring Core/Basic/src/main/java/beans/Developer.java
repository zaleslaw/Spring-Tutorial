package beans;

import java.util.Map;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class Developer extends Engineer {
    private Map<String, Integer> yearsInFramework;
    private boolean isCoffeeConsumer;

    public Developer() {
    }

    public Map<String, Integer> getYearsInFramework() {
        return yearsInFramework;
    }

    public void setYearsInFramework(Map<String, Integer> yearsInFramework) {
        this.yearsInFramework = yearsInFramework;
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
                "yearsInFramework=" + yearsInFramework +
                ", isCoffeeConsumer=" + isCoffeeConsumer +
                "} " + super.toString();
    }
}
