package Before_Dependency_Injection.Ex_3_Factory_Pattern;

import Before_Dependency_Injection.Ex_2_With_Interfaces.interfaces.Human;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey_Zinovyev on 18-Mar-17.
 */
public class DreamTeam_v_3 {
    public static void main(String[] args) {
        List<Human> dreamTeam = new ArrayList<Human>();
        dreamTeam.add(BioReactor.getHuman("SuperHero"));
        dreamTeam.add(BioReactor.getHuman("SuperHero"));
        dreamTeam.add(BioReactor.getHuman("SuperHero"));

        dreamTeam.forEach(Human::speak);
    }
}
