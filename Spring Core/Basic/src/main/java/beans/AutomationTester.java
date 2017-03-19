package beans;

/**
 * Created by Alexey_Zinovyev on 19-Mar-17.
 */
public class AutomationTester extends Engineer {
    private boolean isBro;
    private String favoriteFramework;

    public AutomationTester() {
    }

    public boolean isBro() {
        return isBro;
    }

    public void setIsBro(boolean isBro) {
        this.isBro = isBro;
    }

    public String getFavoriteFramework() {
        return favoriteFramework;
    }

    public void setFavoriteFramework(String favoriteFramework) {
        this.favoriteFramework = favoriteFramework;
    }

    @Override
    public String toString() {
        return "AutomationTester{" +
                "isBro=" + isBro +
                ", favoriteFramework='" + favoriteFramework + '\'' +
                "} " + super.toString();
    }
}
